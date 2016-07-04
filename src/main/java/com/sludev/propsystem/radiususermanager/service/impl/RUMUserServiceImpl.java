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
import org.springframework.stereotype.Service;

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
@Service
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
                && StringUtils.isNoneBlank(Objects.toString(idObj)) )
        {
            throw new RUMException(
                    String.format("ID should not be present '%s'",
                            model.get("id")));
        }

        updateUser(res, model);

        return res;
    }

    public RUMUser updateUser(final Map<String, Object> model) throws RUMException
    {
        RUMUser res = null;

        if( model == null )
        {
            return null;
        }




        updateUser(res, model);

        return res;
    }

    @Override
    public void updateUser(final RUMUser user, final Map<String, Object> model) throws RUMException
    {
        Object usernameObj = model.get("username");
        Object passwordObj = model.get("password");
        Object emailObj    = model.get("email");
        Object lastSeenObj = model.get("lastSeen");
        Object createdObj  = model.get("createdDate");
        Object firstNameObj    = model.get("firstName");
        Object lastNameObj     = model.get("lastName");
        Object mainGroupObj    = model.get("mainGroup");
        Object statusObj       = model.get("status");
        Object enabledObj      = model.get("enabled");
        Object radiusEnabledObj= model.get("radiusEnabled");
        Object canLoginObj     = model.get("canLogin");
        Object lockedObj       = model.get("locked");
        Object lastModifiedObj = model.get("lastModified");
        Object accountNonLockedObj     = model.get("accountNonLocked");
        Object accountNonExpiredObj    = model.get("accountNonExpired");
        Object accountExpiredObj       = model.get("accountExpired");
        Object credentialsNonExpiredObj= model.get("credentialsNonExpired");
        Object credentialsExpiredObj   = model.get("credentialsExpired");

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
        Boolean radiusEnabled = null;
        Boolean canLogin = null;
        Boolean accountNonLocked = null;
        Boolean accountNonExpired = null;
        Boolean accountExpired = null;
        Boolean credentialsNonExpired = null;
        Boolean credentialsExpired = null;
        String lastModifiedStr = null;
        DateTime lastModified = null;

        username = StringUtils.trim(Objects.toString(usernameObj, null));
        password = Objects.toString(passwordObj, null);
        email    = StringUtils.trim(Objects.toString(emailObj, null));
        locked   = BooleanUtils.toBoolean(Objects.toString(lockedObj, null));

        lastSeenStr = Objects.toString(lastSeenObj, null);
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

        createdStr = Objects.toString(createdObj, null);
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

        firstName = StringUtils.trim(Objects.toString(firstNameObj, null));
        lastName = StringUtils.trim(Objects.toString(lastNameObj, null));
        mainGroup = StringUtils.trim(Objects.toString(mainGroupObj, null));

        statusStr = Objects.toString(statusObj, null);
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

        enabled   = BooleanUtils.toBoolean(Objects.toString(enabledObj, null));
        canLogin  = BooleanUtils.toBoolean(Objects.toString(canLoginObj, null));
        radiusEnabled      = BooleanUtils.toBoolean(Objects.toString(radiusEnabledObj, null));
        accountNonLocked   = BooleanUtils.toBoolean(Objects.toString(accountNonLockedObj, null));
        accountNonExpired  = BooleanUtils.toBoolean(Objects.toString(accountNonExpiredObj, null));
        accountExpired     = BooleanUtils.toBoolean(Objects.toString(accountExpiredObj, null));
        credentialsNonExpired  = BooleanUtils.toBoolean(Objects.toString(credentialsNonExpiredObj, null));
        credentialsExpired     = BooleanUtils.toBoolean(Objects.toString(credentialsExpiredObj, null));

        lastModifiedStr = Objects.toString(lastModifiedObj, null);
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
            user.setUsername(username);
        }

        if( StringUtils.isNoneBlank(password))
        {
            user.setPassword(password);
        }

        if( StringUtils.isNoneBlank(email))
        {
            user.setEmail(email);
        }

        if( locked != null )
        {
            user.setLocked(locked);
        }

        if( lastSeen != null )
        {
            user.setLastSeen(lastSeen);
        }

        if( created != null )
        {
            user.setCreatedDate(created);
        }

        if( StringUtils.isNoneBlank(firstName) )
        {
            user.setFirstName(firstName);
        }

        if( StringUtils.isNoneBlank(lastName) )
        {
            user.setLastName(lastName);
        }

        if( StringUtils.isNoneBlank(mainGroup) )
        {
            user.setMainGroup(mainGroup);
        }

        if( status != null )
        {
            user.setStatus(status);
        }

        if( enabled != null )
        {
            user.setEnabled(enabled);
        }

        if( radiusEnabled != null )
        {
            user.setRadiusEnabled(radiusEnabled);
        }

        if( canLogin != null )
        {
            user.setCanLogin(canLogin);
        }

        if( accountNonLocked != null )
        {
            user.setAccountNonLocked(accountNonLocked);
        }

        if( accountNonExpired != null )
        {
            user.setAccountNonExpired(accountNonExpired);
        }

        if( accountExpired != null )
        {
            user.setAccountExpired(accountExpired);
        }

        if( credentialsNonExpired != null )
        {
            user.setCredentialsNonExpired(credentialsNonExpired);
        }

        if( credentialsExpired != null )
        {
            user.setCredExpired(credentialsExpired);
        }

        if( lastModified != null )
        {
            user.setLastModifiedDate(lastModified);
        }

        user.setAccountExpired(false);
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
