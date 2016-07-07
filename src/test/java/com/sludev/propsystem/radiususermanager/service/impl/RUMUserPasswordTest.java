package com.sludev.propsystem.radiususermanager.service.impl;

import com.sludev.propsystem.radiususermanager.RUMWebTestProperties;
import com.sludev.propsystem.radiususermanager.RUMWebTestWatcher;
import com.sludev.propsystem.radiususermanager.entity.RUMRadCheck;
import com.sludev.propsystem.radiususermanager.main.RadiusUserManagerApplication;
import com.sludev.propsystem.radiususermanager.service.RUMRadCheckService;
import com.sludev.propsystem.radiususermanager.util.RUMConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BinaryCodec;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by kervin on 2016-07-06.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RadiusUserManagerApplication.class)
@WebAppConfiguration
public class RUMUserPasswordTest
{
    private static final Logger LOGGER
            = LogManager.getLogger(RUMUserPasswordTest.class);

    @Autowired
    private RUMRadCheckService radCheckService;

    private Properties m_testProperties;
    private Path topTestDir = null;

    @Rule
    public TestWatcher m_testWatcher = new RUMWebTestWatcher();

    @Before
    public void setUp()
            throws IOException
    {
        /**
         * Get the current test properties from a file so we don't hard-code
         * in our source code.
         */
        m_testProperties = RUMWebTestProperties.GetProperties();

        topTestDir = Files.createTempDirectory("rumweb-unit-tests");
        LOGGER.debug(String.format("Created Top Test Directory '%s'", topTestDir));

        try( Stream<Path> paths = Files.list(topTestDir.getParent()) )
        {
            List<Path> dirs = paths.filter(p -> p.getFileName().startsWith("rumweb-unit-tests")
                    && p.equals(topTestDir) == false)
                    .collect(Collectors.toList());

            for( Path p : dirs )
            {
                try
                {
                    FileUtils.deleteDirectory(p.toFile());
                }
                catch( Exception ex )
                {
                    ;
                }
            }
        }
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    @Ignore
    public void A001_changePassword() throws Exception
    {
        String username = UUID.randomUUID().toString();
        RUMRadCheck currRC = radCheckService.createRadCheck(username);

        currRC.setAttribute(RUMConstants.PASSWORD_HASH_DEFAULT);

        RUMUserPassword up = new RUMUserPassword();
        up.changePassword(currRC, "passme");
    }

    @Test
    @Ignore
    public void A002_changePassword() throws Exception
    {
        String salt = "salt";
        String pass = "passme";

        byte[] shaRes = DigestUtils.sha1(pass.concat(salt));

        LOGGER.debug(String.format("Hex Res = '%s'\n",
                            Hex.encodeHexString(shaRes)));

        shaRes = ArrayUtils.addAll(shaRes, salt.getBytes());
        String res = Base64.encodeBase64String(shaRes);

        LOGGER.debug(String.format("Base64 Res = '%s'\n", res));

        Assert.assertEquals(res, "bXUygZ+GToKwJysZyzghIEwf9tJzYWx0");
    }
}