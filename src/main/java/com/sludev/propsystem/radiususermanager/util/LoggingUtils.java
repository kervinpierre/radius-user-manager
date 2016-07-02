package com.sludev.propsystem.radiususermanager.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kervin on 2016-05-07.
 */
public final class LoggingUtils
{
    private static final Logger LOGGER = LogManager.getLogger(LoggingUtils.class);

    public static void logRequestDebug(HttpServletRequest request)
    {
        Enumeration enames = request.getHeaderNames();
        StringBuilder logStr = new StringBuilder();

        logStr.append("\nRequest\n=======\n");
        while(enames.hasMoreElements())
        {
            String name = (String) enames.nextElement();
            logStr.append(String.format("'%s' : '%s'\n", name,
                                        request.getHeader(name)));
        }

        enames = request.getParameterNames();

        logStr.append("\nParameters\n=======\n");
        while(enames.hasMoreElements())
        {
            String name = (String) enames.nextElement();
            logStr.append(String.format("'%s' : '%s'\n", name,
                    Arrays.toString(request.getParameterValues(name))));
        }

        HttpSession sess = request.getSession();
        enames = sess.getAttributeNames();

        logStr.append("\nSession\n=======\n");
        while(enames.hasMoreElements())
        {
            String name = (String) enames.nextElement();
            logStr.append(String.format("'%s' : '%s'\n", name,
                                        sess.getAttribute(name)));
        }

        LOGGER.debug(logStr.toString());
    }
}
