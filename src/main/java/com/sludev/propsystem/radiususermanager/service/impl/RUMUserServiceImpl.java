package com.sludev.propsystem.radiususermanager.service.impl;

import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.repository.RUMUserRepository;
import com.sludev.propsystem.radiususermanager.service.RUMUserService;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * * The actual implementation of the user Service layer interface.
 *
 * @author Kervin Pierre <info@sludev.com>
 *     
 * Created by kervin on 2016-04-30.
 */
@Component
public class RUMUserServiceImpl implements RUMUserService
{
    private static final Logger LOGGER = LogManager.getLogger(RUMUserService.class);

    @Autowired(required = true)
    private RUMUserRepository userRepository;

    @Override
    public RUMUser findByUsername(String userName)
    {
        return userRepository.findByUsername(userName);
    }

    public RUMUser getOne(UUID uuid)
    {
        return userRepository.getOne(uuid);
    }

//    @Override
//    public RUMUser validateCredential(String username, String password)
//    {
//        return userRepository.findByUsernameAndPassword(username, password);
//    }

    @Override
    public List<RUMUser> findAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public RUMUser createUser(final Map<String, Object> model) throws RUMException
    {
        RUMUser res = new RUMUser();

        if( model == null )
        {
            return null;
        }

        Object idObj = model.get("id");

        if( idObj != null
                && Objects.toString(idObj) != null
                && StringUtils.isNoneBlank(Objects.toString(idObj)))
        {
            throw new RUMException(
                    String.format("ID should not be present '%s'",
                                    model.get("id")));
        }

        Object usernameObj = model.get("username");
        Object passwordObj = model.get("password");
        Object emailObj    = model.get("email");
        Object lockedObj   = model.get("locked");
        Object lastSeenObj = model.get("lastSeen");
        Object createdObj  = model.get("created");
        Object firstNameObj    = model.get("firstName");
        Object lastNameObj     = model.get("lastName");
        Object mainGroupObj    = model.get("mainGroup");
        Object statusObj       = model.get("status");
        Object enabledObj      = model.get("enabled");
        Object canLoginObj     = model.get("canLogin");
        Object lastModifiedObj = model.get("lastModified");

        String username = null;
        String password = null;
        String email = null;
        Boolean locked = null;
        String lastSeenStr = null;
        DateTime lastSeen = null;
        String createdStr = null;
        DateTime created = null;
        String firstName = null;
        String lastName = null;
        String mainGroup = null;
        String statusStr = null;
        Integer status = null;
        Boolean enabled = null;
        Boolean canLogin = null;
        String lastModifiedStr = null;
        DateTime lastModified = null;

        username = StringUtils.trim(Objects.toString(usernameObj));
        password = Objects.toString(passwordObj);
        email    = StringUtils.trim(Objects.toString(emailObj));
        locked   = BooleanUtils.toBoolean(Objects.toString(lockedObj));

        lastSeenStr = Objects.toString(lastSeenObj);
        if( StringUtils.isNoneBlank(lastSeenStr) )
        {
            try
            {
                // lastSeen = Date.from(Instant.parse(Objects.toString(lastSeenObj)));
                lastSeen = DateTime.parse(lastSeenStr);
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing '%s'", lastSeenStr), ex);
            }
        }

        createdStr = Objects.toString(createdObj);
        if( StringUtils.isNoneBlank(createdStr) )
        {
            try
            {
                //created = Date.from(Instant.parse(Objects.toString(createdObj)));
                created = DateTime.parse(createdStr);
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing '%s'", createdStr), ex);
            }
        }

        firstName = StringUtils.trim(Objects.toString(firstNameObj));
        lastName = StringUtils.trim(Objects.toString(lastNameObj));
        mainGroup = StringUtils.trim(Objects.toString(mainGroupObj));

        statusStr = Objects.toString(statusObj);
        if( StringUtils.isNoneBlank(statusStr) )
        {
            try
            {
                status = Integer.parseInt(statusStr);
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing '%s'", statusStr), ex);
            }
        }

        enabled   = BooleanUtils.toBoolean(Objects.toString(enabledObj));
        canLogin  = BooleanUtils.toBoolean(Objects.toString(canLoginObj));

        lastModifiedStr = Objects.toString(lastModifiedObj);
        if( StringUtils.isNoneBlank(lastModifiedStr) )
        {
            try
            {
                //lastModified = Date.from(Instant.parse(Objects.toString(lastModifiedObj)));
                lastModified = DateTime.parse(lastModifiedStr);
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing '%s'", lastModifiedStr), ex);
            }
        }

        if( StringUtils.isNoneBlank(username))
        {
            res.setUsername(username);
        }

        if( StringUtils.isNoneBlank(password))
        {
            res.setPassword(password);
        }

        if( StringUtils.isNoneBlank(email))
        {
            res.setEmail(email);
        }

        if( locked != null )
        {
            res.setLocked(locked);
        }

        if( lastSeen != null )
        {
            res.setLastSeen(lastSeen);
        }

        if( created != null )
        {
            res.setCreatedDate(created);
        }

        if( StringUtils.isNoneBlank(firstName) )
        {
            res.setFirstName(firstName);
        }

        if( StringUtils.isNoneBlank(lastName) )
        {
            res.setLastName(lastName);
        }

        if( StringUtils.isNoneBlank(mainGroup) )
        {
            res.setMainGroup(mainGroup);
        }

        if( status != null )
        {
            res.setStatus(status);
        }

        if( enabled != null )
        {
            res.setEnabled(enabled);
        }

        if( canLogin != null )
        {
            res.setCanLogin(canLogin);
        }

        if( lastModified != null )
        {
            res.setLastModifiedDate(lastModified);
        }

        res.setAccountExpired(false);

        return res;
    }

    @Override
    public RUMUser createUser(String username)
    {
        RUMUser res = new RUMUser();

        res.setUsername(username);
        res.setAccountExpired(false);
        res.setCanLogin(false);

        return res;
    }

    @Override
    public RUMUser saveAndFlush(RUMUser RUMUser)
    {
        RUMUser res = userRepository.saveAndFlush(RUMUser);

        return res;
    }

    @Override
    public void flush()
    {
        userRepository.flush();
    }

    @Override
    public void updateUser(RUMUser RUMUser)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteUser(RUMUser RUMUser)
    {
        userRepository.delete(RUMUser);
    }
}
