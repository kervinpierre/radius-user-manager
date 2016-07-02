package com.sludev.propsystem.radiususermanager.controller;

import com.sludev.propsystem.radiususermanager.util.LoggingUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kervin on 2016-04-29.
 */
@Controller("/")
public class IndexController
{
    private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

    @RequestMapping("/")
    public String index(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        return "index";
    }
}
