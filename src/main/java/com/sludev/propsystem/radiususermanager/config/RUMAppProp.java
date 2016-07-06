package com.sludev.propsystem.radiususermanager.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by kervin on 2016-05-12.
 */
@Component
public final class RUMAppProp
{
    private static final Logger LOGGER = LogManager.getLogger(RUMAppProp.class);

    @Value("${rum.ignore-ssl-errors:false}")
    private Boolean rumIgnoreSSLErrors;

    @Value("${rum.password.hash}")
    private String rumPasswordHashStr;

    public Boolean getRumIgnoreSSLErrors()
    {
        return rumIgnoreSSLErrors;
    }

    public String getRumPasswordHashStr()
    {
        return rumPasswordHashStr;
    }

}
