package com.sludev.propsystem.radiususermanager.controller.admin;

import com.sludev.propsystem.radiususermanager.entity.AdminConfig;
import com.sludev.propsystem.radiususermanager.service.AdminConfigService;
import com.sludev.propsystem.radiususermanager.util.LoggingUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kervin on 2016-04-29.
 */
@Controller("/admin")
public class AdminController
{
    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @Autowired
    private AdminConfigService adminConfigService;

    @RequestMapping("/admin")
    public ModelAndView admin(HttpServletRequest request,
                              @RequestParam(value = "saveButton", required = false) String savedStr,
                              @ModelAttribute("AdminConfig")AdminConfig config,
                              ModelMap model)
    {
        LoggingUtils.logRequestDebug(request);

        AdminConfig currConf = config;
        if( currConf == null )
        {
            currConf = new AdminConfig();
        }

        if( StringUtils.equals(savedStr, "saved") )
        {
            adminConfigService.saveAndFlush(currConf);
        }

        return  new ModelAndView("admin/index", "command", currConf);
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
