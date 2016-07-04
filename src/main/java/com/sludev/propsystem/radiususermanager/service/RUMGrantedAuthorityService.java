package com.sludev.propsystem.radiususermanager.service;

import com.sludev.propsystem.radiususermanager.entity.RUMGrantedAuthority;
import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The service layer interface for the User-Service implementation.
 *
 * This layer essentially hides the repository from higher layers and also provides
 * business logic to these higher layers.
 *
 * @author Kervin Pierre <info@sludev.com>
 * Created by kervin on 2016-04-30.
 */
@Service
public interface RUMGrantedAuthorityService
{
    RUMGrantedAuthority findByAuthority(String authname);
    RUMGrantedAuthority getOne(UUID uuid);

    RUMGrantedAuthority createAuthority(String authname);

    RUMGrantedAuthority saveAndFlush(RUMGrantedAuthority grantedAuthority);
    void flush();
    void updateAuthority(RUMGrantedAuthority grantedAuthority);
    void deleteAuthority(RUMGrantedAuthority grantedAuthority);
}
