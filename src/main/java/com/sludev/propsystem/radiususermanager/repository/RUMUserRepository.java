package com.sludev.propsystem.radiususermanager.repository;

import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Created by kervin on 2016-04-30.
 */
@Repository
public interface RUMUserRepository  extends JpaRepository<RUMUser, UUID>
{
    @Query("from RUMUser")
    public List<RUMUser> findAllUsers();

    public RUMUser findByUsername(String username);
   // public RUMUser findByUsernameAndPassword(String username, String password);
}

