package com.sludev.propsystem.radiususermanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by kervin on 2016-04-30.
 */
public final class RUMException extends Exception
{
    private static final Logger LOGGER = LogManager.getLogger(RUMException.class);

    public RUMException(String msg)
    {
        super(msg);
    }

    public RUMException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
