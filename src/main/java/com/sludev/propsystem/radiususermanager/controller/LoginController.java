package com.sludev.propsystem.radiususermanager.controller;

import com.sludev.propsystem.radiususermanager.util.LoggingUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
@Controller("/login")
public class LoginController
{
    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model,
           @RequestParam(name="error", required=false, defaultValue="false")Boolean hasError)
    {
        LoggingUtils.logRequestDebug(request);

        String lastError = "";
        if( BooleanUtils.isTrue(hasError) )
        {
            Object ex = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if( ex == null )
            {
                lastError = "Unknown error";
            }
            else
            {
                lastError = StringUtils.defaultString(ex.toString(), "");
            }
        }

        model.addAttribute("lastError", lastError);

        return "login";
    }

    @RequestMapping("/logout")
    public String logout()
    {
        return "logout";
    }
}
