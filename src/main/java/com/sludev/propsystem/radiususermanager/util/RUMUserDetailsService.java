package com.sludev.propsystem.radiususermanager.util;

import com.sludev.propsystem.radiususermanager.entity.RUMGrantedAuthority;
import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.service.RUMGrantedAuthorityService;
import com.sludev.propsystem.radiususermanager.service.RUMUserService;
import com.sludev.propsystem.radiususermanager.service.impl.RUMUserPassword;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kervin on 2016-04-30.
 */
@Component
public final class RUMUserDetailsService implements UserDetailsService
{
    private static final Logger LOGGER = LogManager.getLogger(RUMUserDetailsService.class);

    @Autowired(required = true)
    private RUMUserService userService;

    @Autowired(required = true)
    private RUMGrantedAuthorityService authService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        // FIXME : SECURITY : Regex the 'username' to make sure it's not an
        // attack

        RUMUser user = userService.findByUsername(userName);

        if (user == null)
        {
            throw new UsernameNotFoundException(userName + " not found.");
        }

        //RUMUserPassword up = new RUMUserPassword();
        //up.validatePassword(user, )

        return user;
    }
    
    public void addDefaultAdminUser() throws RUMException
    {
        RUMUser user = userService.findByUsername("radmin");
        if(user != null)
        {
            return;
        }

        user = userService.createUser("radmin");
        user.setCreatedDate(DateTime.now());
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setAccountExpired(false);
        user.setCanLogin(true);
        user.setEnabled(true);
        user.setLocked(false);
        user.setCredentialsNonExpired(true);
        user.setCredExpired(false);

        Set<RUMGrantedAuthority> authSet = new HashSet<>();
        RUMGrantedAuthority auth = authService.createAuthority("ROLE_ADMIN");
        auth.setRumUser(user);
        authSet.add(auth);
        user.setUserAuthorities(authSet);

        RUMUserPassword up = new RUMUserPassword();
        String pass = "test"; // up.generatePassword(user);
        up.changePassword(user, "test");

        LOGGER.info(String.format("Generated secret : '%s'", pass));

        authService.saveAndFlush(auth);
        userService.saveAndFlush(user);
    }

}
