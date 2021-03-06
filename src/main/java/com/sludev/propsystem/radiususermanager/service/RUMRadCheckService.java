package com.sludev.propsystem.radiususermanager.service;

import com.sludev.propsystem.radiususermanager.entity.RUMRadCheck;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
public interface RUMRadCheckService
{
    RUMRadCheck findByUsername(String username);
    RUMRadCheck getOne(int id);
    // RUMRadCheck validateCredential(String username, String password);

    List<RUMRadCheck> findAllRadChecks();
    Page<RUMRadCheck> findAllRadChecks(Pageable pageable);
    Page<RUMRadCheck> findAllRadChecks(Specification<RUMRadCheck> spec, Pageable pageable);

    void updateRadCheck(RUMRadCheck rc, Map<String, Object> model) throws RUMException;

    RUMRadCheck createRadCheck(String username);
    RUMRadCheck createRadCheck(Map<String, Object> model) throws RUMException;

    List<String> findAllUsernames();

    RUMRadCheck saveAndFlush(RUMRadCheck RUMRadCheck);
    void flush();
    void updateRadCheck(RUMRadCheck RUMRadCheck);
    void deleteRadCheck(RUMRadCheck RUMRadCheck);
}
