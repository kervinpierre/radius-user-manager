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

import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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
