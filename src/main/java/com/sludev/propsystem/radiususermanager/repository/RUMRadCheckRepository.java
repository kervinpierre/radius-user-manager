package com.sludev.propsystem.radiususermanager.repository;

import com.sludev.propsystem.radiususermanager.entity.RUMRadCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kervin on 2016-04-30.
 */
@Repository
public interface RUMRadCheckRepository extends JpaRepository<RUMRadCheck, Integer>
{
    @Query("from RUMRadCheck")
    public List<RUMRadCheck> findAllRadCheck();

    public RUMRadCheck findByUsername(String username);
   // public RUMRadCheck findByUsernameAndPassword(String username, String password);
}

