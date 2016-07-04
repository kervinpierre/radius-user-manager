package com.sludev.propsystem.radiususermanager.service.impl;

import com.sludev.propsystem.radiususermanager.entity.RUMGrantedAuthority;
import com.sludev.propsystem.radiususermanager.repository.RUMGrantedAuthorityRepository;
import com.sludev.propsystem.radiususermanager.service.RUMGrantedAuthorityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by kervin on 2016-07-03.
 */
@Service
public class RUMGrantedAuthorityServiceImpl implements RUMGrantedAuthorityService
{
    private static final Logger LOGGER = LogManager.getLogger(RUMGrantedAuthorityServiceImpl.class);

    @Autowired(required = true)
    private RUMGrantedAuthorityRepository authRepository;

    @Override
    public RUMGrantedAuthority findByAuthority(String authname)
    {
        return null;
    }

    @Override
    public RUMGrantedAuthority getOne(UUID uuid)
    {
        return authRepository.getOne(uuid);
    }

    @Override
    public RUMGrantedAuthority createAuthority(String authname)
    {
        RUMGrantedAuthority res = new RUMGrantedAuthority();
        res.setAuthority(authname);

        return res;
    }

    @Override
    public RUMGrantedAuthority saveAndFlush(RUMGrantedAuthority grantedAuthority)
    {
        RUMGrantedAuthority res = authRepository.saveAndFlush(grantedAuthority);

        return res;
    }

    @Override
    public void flush()
    {
        authRepository.flush();
    }

    @Override
    public void updateAuthority(RUMGrantedAuthority grantedAuthority)
    {

    }

    @Override
    public void deleteAuthority(RUMGrantedAuthority grantedAuthority)
    {
        authRepository.delete(grantedAuthority);
    }
}
