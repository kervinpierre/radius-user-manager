package com.sludev.propsystem.radiususermanager.repository;

import com.sludev.propsystem.radiususermanager.entity.RUMGrantedAuthority;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Created by kervin on 2016-07-03.
 */
public interface RUMGrantedAuthorityRepository extends JpaRepository<RUMGrantedAuthority, UUID>
{
    @Query("from RUMGrantedAuthority")
    public List<RUMGrantedAuthority> findAllAuthorities();

   // public RUMGrantedAuthority findByAuthority(String authname);
}
