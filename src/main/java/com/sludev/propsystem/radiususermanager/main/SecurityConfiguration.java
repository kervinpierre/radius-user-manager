package com.sludev.propsystem.radiususermanager.main;

import com.sludev.propsystem.radiususermanager.service.impl.RUMUserPassword;
import com.sludev.propsystem.radiususermanager.util.RUMUserDetailsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by kervin on 2016-04-30.
 */
@Configuration
public class SecurityConfiguration  extends WebSecurityConfigurerAdapter
{
    private static final Logger LOGGER = LogManager.getLogger(SecurityConfiguration.class);

    @Autowired(required = true)
    RUMUserDetailsService userDetailsService;

    @Autowired( required = true)
    RUMUserPassword userPassword;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .authorizeRequests()
                .antMatchers("/",
                        "/css/**",
                        "/js/**",
                        "/shared/**",
                        "/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/api/admin/**").hasRole("ADMIN")
                //.antMatchers("/").access("hasRole('READER')")
                //.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?error=true")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception
    {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(userPassword);

        // Add a default admin if none is available
        userDetailsService.addDefaultAdminUser();
    }
}
