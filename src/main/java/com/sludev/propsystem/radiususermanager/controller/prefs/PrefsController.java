package com.sludev.propsystem.radiususermanager.controller.prefs;

import com.sludev.propsystem.radiususermanager.config.RUMAppProp;
import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.service.AdminConfigService;
import com.sludev.propsystem.radiususermanager.service.RUMRadCheckService;
import com.sludev.propsystem.radiususermanager.service.RUMUserService;
import com.sludev.propsystem.radiususermanager.service.impl.RUMUserPassword;
import com.sludev.propsystem.radiususermanager.util.LoggingUtils;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kervin on 2016-04-29.
 */
@Controller("/prefs")
public class PrefsController
{
    private static final Logger LOGGER = LogManager.getLogger(PrefsController.class);

    @Autowired
    private RUMAppProp appProps;

    @Autowired
    private RUMRadCheckService radCheckService;

    @Autowired
    private AdminConfigService adminConfigService;

    @Autowired
    private RUMUserService userService;

    @RequestMapping("/prefs")
    public String prefs(HttpServletRequest request,
                              @RequestParam(value = "saveButton", required = false) String savedStr,
                              @RequestParam(value = "P1", required = false) String p1Str) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        UsernamePasswordAuthenticationToken  puser = (UsernamePasswordAuthenticationToken)request.getUserPrincipal();
        if( puser == null
                || puser.isAuthenticated() == false )
        {
            return "/index";
        }

        RUMUser user = (RUMUser)puser.getPrincipal();
        if( user == null )
        {
            return "/index";
        }

        if( StringUtils.equals(savedStr, "saved")
                && StringUtils.isNoneBlank(p1Str))
        {
            RUMUserPassword up = new RUMUserPassword();
            up.changePassword(user, p1Str);
            userService.saveAndFlush(user);

            RUMUserPassword.changePassword(user, p1Str,
                    userService, radCheckService,
                    appProps.getRumPasswordHashStr(),
                    true);

            return "prefs/success";
        }

        return "prefs/index";
    }

//    /**
//     * Save then redirect to filled form.
//     *
//     * @param config
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/admin/save", method = RequestMethod.POST)
//    public String save(@ModelAttribute("SpringWeb")AdminConfig config,
//                             ModelMap model)
//    {
//        model.addAttribute("weWorkDoneDir", config.getWeWorkDoneDir());
//        model.addAttribute("id", config.getId());
//
//        return "adminSave";
//    }
}
