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

    @Value("${psys.phantomjs.ignore-ssl-errors:false}")
    private Boolean phantomjsIgnoreSSLErrors;

    @Value("${psys.targets.wework.script.name:wework.js}")
    private String phantomjsScriptNameStr;

    public Boolean getPhantomjsIgnoreSSLErrors()
    {
        return phantomjsIgnoreSSLErrors;
    }

    public String getPhantomJSScriptNameStr()
    {
        return phantomjsScriptNameStr;
    }

}
