package com.sludev.propsystem.radiususermanager.controller.api;

import com.sludev.propsystem.radiususermanager.config.RUMAppProp;
import com.sludev.propsystem.radiususermanager.entity.AdminConfig;
import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.service.AdminConfigService;
import com.sludev.propsystem.radiususermanager.service.RUMUserService;
import com.sludev.propsystem.radiususermanager.util.LoggingUtils;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import com.sludev.propsystem.radiususermanager.util.kendo.DatasourceVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by kervin on 2016-06-23.
 */
@Controller("/api")
public final class ApiController
{
    private static final Logger LOGGER = LogManager.getLogger(ApiController.class);

    @Autowired
    private RUMAppProp appProps;

    @Autowired
    private AdminConfigService adminConfigService;

    @Autowired
    private RUMUserService userService;

    @ResponseBody
    @RequestMapping(value = "/api/admin-config/", method = RequestMethod.GET)
    public DatasourceVO adminConfigList(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        DatasourceVO res = DatasourceVO.from();

        res.data = adminConfigService.findAll().toArray();
        res.setSchemaModelId("id");
        res.total = res.data.length;

        res.validate();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/read-admin-config", method = RequestMethod.POST)
    public DatasourceVO ReadAdminConfig(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        DatasourceVO res = DatasourceVO.from();

        res.data = adminConfigService.findAll().toArray();
        res.setSchemaModelId("id");
        res.total = res.data.length;

        res.validate();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/read-all-users", method = RequestMethod.POST)
    public DatasourceVO ReadAllUsers(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        DatasourceVO res = DatasourceVO.from();

        res.data = userService.findAllUsers().toArray();
        //res.setSchemaModelId("id");
        res.total = res.data.length;
        //res.pageSize = 2;
        //res.serverPaging = true;

        res.validate();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/update-user", method = RequestMethod.POST)
    public RUMUser UpdateUser(HttpServletRequest request,
                                   @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        Object uuidObj = model.get("id");
        if( uuidObj == null )
        {
            throw new RUMException("Missing ID in 'update-user'");
        }

        RUMUser res = null;

        Object idObj = model.get("id");

        if( idObj == null
                || Objects.toString(idObj) == null
                || StringUtils.isBlank(Objects.toString(idObj)) )
        {
            throw new RUMException("ID is missing.");
        }

        String idStr = Objects.toString(idObj);
        UUID id = null;

        try
        {
            id = UUID.fromString(idStr);
        }
        catch( Exception ex )
        {
            LOGGER.debug(String.format("Invalid UUID '%s'", idStr), ex);
        }

        res = userService.getOne(id);
        if( res == null )
        {
            LOGGER.debug(String.format("User with ID not found '%s'", id));
        }

        userService.updateUser(res, model);
        if( res != null )
        {
            userService.saveAndFlush(res);
        }


        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/create-user", method = RequestMethod.POST)
    public RUMUser CreateUser(HttpServletRequest request,
                                   @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        RUMUser res = null;

        res = userService.createUser(model);
        if( res != null )
        {
            userService.saveAndFlush(res);
        }

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin-config/first", method = RequestMethod.GET)
    public AdminConfig adminConfigFirst(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        return adminConfigService.findFirstByOrderByLabel();
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin-config/{id}", method = RequestMethod.GET)
    public AdminConfig adminConfigByID(HttpServletRequest request,
                                       @PathVariable(value = "id")UUID id)
    {
        LoggingUtils.logRequestDebug(request);

        return adminConfigService.findById(id);
    }
}
