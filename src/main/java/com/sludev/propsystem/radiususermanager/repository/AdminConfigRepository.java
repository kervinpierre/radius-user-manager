package com.sludev.propsystem.radiususermanager.repository;

import com.sludev.propsystem.radiususermanager.entity.AdminConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by kervin on 2016-05-18.
 */
@Repository
public interface AdminConfigRepository extends JpaRepository<AdminConfig, UUID>
{
    //@Query("from scrape_input")
   public List<AdminConfig> findAll();

    public AdminConfig findById(UUID id);
    public AdminConfig findFirstByOrderByLabel();
}

