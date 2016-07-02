package com.sludev.propsystem.radiususermanager.service;

import com.sludev.propsystem.radiususermanager.entity.AdminConfig;

import java.util.List;
import java.util.UUID;

/**
 * The service layer interface for the User-Service implementation.
 *
 * This layer essentially hides the repository from higher layers and also provides
 * business logic to these higher layers.
 *
 * @author Kervin Pierre <info@sludev.com>
 * Created by kervin on 2016-05-18.
 */
public interface AdminConfigService
{
        AdminConfig findById(UUID id);
        AdminConfig findFirstByOrderByLabel();
        List<AdminConfig> findAll();

        AdminConfig createAdminConfig(String label);
        AdminConfig saveAndFlush(AdminConfig input);
        void flush();
        void updateAdminConfig(AdminConfig input);
        void deleteAdminConfig(AdminConfig input);
}
