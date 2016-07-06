/*
 *  SLU Dev Inc. CONFIDENTIAL
 *  DO NOT COPY
 * 
 * Copyright (c) [2012] - [2014] SLU Dev Inc. <info@sludev.com>
 * All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 *  the property of SLU Dev Inc. and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to SLU Dev Inc. and its suppliers and
 *  may be covered by U.S. and Foreign Patents, patents in process,
 *  and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from SLU Dev Inc.
 */
package com.sludev.propsystem.radiususermanager.service.impl;

import com.sludev.propsystem.radiususermanager.entity.RUMRadCheck;
import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.service.RUMRadCheckService;
import com.sludev.propsystem.radiususermanager.service.RUMUserService;
import com.sludev.propsystem.radiususermanager.util.RUMConstants;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * API for managing hashed user passwords.
 * 
 * @author Kervin Pierre <info@sludev.com>
 */
@Service
public class RUMUserPassword extends BCryptPasswordEncoder
{
	private static final int DEFAULT_PASSWORD_LENGTH = 10;

    private static final Logger LOGGER = LogManager.getLogger(RUMUserPassword.class);

	/**
	 * Changes a user's hashed password.
	 * 
	 * @param usr
	 *            User who is getting a new password.
	 * @param pss
	 *            Plaintext password.
	 * 
	 */
	public void changePassword(RUMUser usr, String pss)
    {
		String pass = encode(pss);

		usr.setPassword(pass);
	}

	public void changePassword(RUMRadCheck check, String pss) throws RUMException
    {
        String attr = StringUtils.lowerCase(check.getAttribute());

        if( StringUtils.isBlank(attr) )
        {
            throw new RUMException("Password attribute name cannot be empty");
        }

        String currPass = pss;

        switch( attr )
        {
            case "cleartext-password":
                break;

            case "ssha-password":
                {
                    // Salted SHA1 with a random salt appended
                    String salt = UUID.randomUUID().toString().replaceAll("-", "");
                    currPass = Base64.encodeBase64String(ArrayUtils.addAll(
                                                DigestUtils.sha1(currPass.concat(salt)),
                                                    salt.getBytes()));
                }
                break;

            default:
                throw new RUMException(String.format("Invalid attribute name '%s'", attr));
        }

        check.setValue(currPass);
        check.setOp(":=");
    }

    public static void changePassword(RUMUser usr,
                                      String pass,
                                      RUMUserService userService,
                                      RUMRadCheckService radCheckService,
                                      String defaultHash,
                                      boolean validate) throws RUMException
    {
        if( StringUtils.isBlank(pass) )
        {
            throw new RUMException("Password cannot be blank");
        }

        if( validate )
        {
            Matcher matcher = Pattern.compile(RUMConstants.PASSWORD_REGEX).matcher(pass);
            if( matcher.matches() == false )
            {
                throw new RUMException(String.format("Password '%s' is invalid", pass));
            }
        }

        RUMUserPassword up = new RUMUserPassword();
        up.changePassword(usr, pass);

        if( BooleanUtils.isTrue(usr.isRadiusEnabled()) )
        {
            RUMRadCheck currRC = radCheckService.findByUsername(usr.getUsername());
            if( currRC == null )
            {
                LOGGER.warn(String.format("RadCheck not found for user '%s'",
                        usr.getUsername()));

                currRC = radCheckService.createRadCheck(usr.getUsername());
            }

            if( StringUtils.isBlank(currRC.getAttribute()) )
            {
                String currAttr = defaultHash;
                if( StringUtils.isBlank(currAttr) )
                {
                    currAttr = RUMConstants.PASSWORD_HASH_DEFAULT;
                }
                currRC.setAttribute(currAttr);
            }

            up.changePassword(currRC, pass);
            radCheckService.saveAndFlush(currRC);
        }

        userService.saveAndFlush(usr);
    }

    /**
	 * Generate a new random password for a user and return that.
	 * 
	 * @param usr
	 *            The user receiving the new password
	 * @return The newly generated password.
	 * @throws RUMException
	 */
	public String generatePassword(RUMUser usr) throws RUMException
    {
		String res;

		res = RandomStringUtils.randomAlphanumeric(DEFAULT_PASSWORD_LENGTH);
		changePassword(usr, res);

		return res;
	}

	/**
	 * Check that a user's password is valid against the hashed password in the
	 * database.
	 * 
	 * @param usr
	 *            User for checking
	 * @param pss
	 *            Plaintext password for checking
	 * @return true if the user's password is valid. Otherwise false.
	 * @throws RUMException
	 */
	public boolean validatePassword(RUMUser usr, String pss) throws RUMException
	{
		boolean res = false;
		String currHash = usr.getPassword();

		res = this.matches(pss, currHash);

		return res;
	}

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword)
    {
        return super.matches(rawPassword, encodedPassword);
    }
}
