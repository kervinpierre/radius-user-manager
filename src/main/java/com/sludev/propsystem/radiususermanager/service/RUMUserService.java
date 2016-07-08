package com.sludev.propsystem.radiususermanager.service;

import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
public interface RUMUserService
{
    RUMUser findByUsername(String username);
    RUMUser getOne(UUID uuid);
   // RUMUser validateCredential(String username, String password);

    List<RUMUser> findAllUsers();
    Page<RUMUser> findAllUsers(Pageable pageable);
    Page<RUMUser> findAllUsers(Specification<RUMUser> spec, Pageable pageable);

    List<String> findAllUsernames();
    List<String> findAllFirstnames();
    List<String> findAllLastnames();

    void updateUser(RUMUser user, Map<String, Object> model) throws RUMException;

    RUMUser createUser(String username);
    RUMUser createUser(Map<String, Object> model) throws RUMException;

    RUMUser saveAndFlush(RUMUser RUMUser);
    void flush();
    void updateUser(RUMUser RUMUser);
    void deleteUser(RUMUser RUMUser);
}
