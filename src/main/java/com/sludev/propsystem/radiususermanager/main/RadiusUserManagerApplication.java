package com.sludev.propsystem.radiususermanager.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages="com.sludev.propsystem.radiususermanager")
@EnableJpaRepositories("com.sludev.propsystem.radiususermanager.repository")
@EntityScan(basePackages = "com.sludev.propsystem.radiususermanager.entity")
public class RadiusUserManagerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RadiusUserManagerApplication.class, args);
    }
}
