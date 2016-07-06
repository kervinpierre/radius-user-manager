package com.sludev.propsystem.radiususermanager.controller.api.admin;

import com.sludev.propsystem.radiususermanager.config.RUMAppProp;
import com.sludev.propsystem.radiususermanager.entity.AdminConfig;
import com.sludev.propsystem.radiususermanager.entity.RUMRadCheck;
import com.sludev.propsystem.radiususermanager.entity.RUMUser;
import com.sludev.propsystem.radiususermanager.service.AdminConfigService;
import com.sludev.propsystem.radiususermanager.service.RUMRadCheckService;
import com.sludev.propsystem.radiususermanager.service.RUMUserService;
import com.sludev.propsystem.radiususermanager.service.impl.RUMUserPassword;
import com.sludev.propsystem.radiususermanager.util.LoggingUtils;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import com.sludev.propsystem.radiususermanager.util.kendo.DatasourceVO;
import org.apache.commons.lang3.BooleanUtils;
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by kervin on 2016-06-23.
 */
@Controller("/api/admin")
public final class ApiAdminController
{
    private static final Logger LOGGER = LogManager.getLogger(ApiAdminController.class);

    @Autowired
    private RUMAppProp appProps;

    @Autowired
    private AdminConfigService adminConfigService;

    @Autowired
    private RUMUserService userService;

    @Autowired
    private RUMRadCheckService radCheckService;

    @ResponseBody
    @RequestMapping(value = "/api/admin/admin-config/", method = RequestMethod.GET)
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
    @RequestMapping(value = "/api/admin/read-admin-config", method = RequestMethod.POST)
    public DatasourceVO readAdminConfig(HttpServletRequest request)
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
    @RequestMapping(value = "/api/admin/read-all-users", method = RequestMethod.POST)
    public DatasourceVO readAllUsers(HttpServletRequest request)
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
    @RequestMapping(value = "/api/admin/read-all-radchecks", method = RequestMethod.POST)
    public DatasourceVO readAllRadChecks(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        DatasourceVO res = DatasourceVO.from();

        res.data = radCheckService.findAllRadCheck().toArray();
        //res.setSchemaModelId("id");
        res.total = res.data.length;
        //res.pageSize = 2;
        //res.serverPaging = true;

        res.validate();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/update-user", method = RequestMethod.POST)
    public RUMUser updateUser(HttpServletRequest request,
                                   @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

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
    @RequestMapping(value = "/api/admin/update-radcheck", method = RequestMethod.POST)
    public RUMRadCheck updateRadCheck(HttpServletRequest request,
                              @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        RUMRadCheck res = null;

        Object idObj = model.get("id");

        if( idObj == null
                || Objects.toString(idObj) == null
                || StringUtils.isBlank(Objects.toString(idObj)) )
        {
            throw new RUMException("ID is missing.");
        }

        String idStr = Objects.toString(idObj);
        Integer id = null;

        try
        {
            id = Integer.parseInt(idStr);
        }
        catch( Exception ex )
        {
            LOGGER.debug(String.format("Invalid Integer '%s'", idStr), ex);
        }

        res = radCheckService.getOne(id);
        if( res == null )
        {
            LOGGER.debug(String.format("RadCheck with ID not found '%s'", id));
        }

        radCheckService.updateRadCheck(res, model);
        if( res != null )
        {
            radCheckService.saveAndFlush(res);
        }

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/change-pass", method = RequestMethod.POST)
    public Boolean changePass(HttpServletRequest request,
                              @RequestBody Map<String, Object> model) throws RUMException
    {
        Boolean res = false;

        Object passObj = model.get("p1");
        if( passObj == null )
        {
            return false;
        }

        String pass = passObj.toString();
        if( StringUtils.isBlank(pass))
        {
            return false;
        }

        Object idObj = model.get("id");
        if( idObj == null )
        {
            return false;
        }

        String id = idObj.toString();
        if( StringUtils.isBlank(id))
        {
            return false;
        }

        UUID uid = null;

        try
        {
            uid = UUID.fromString(id);
        }
        catch( Exception ex )
        {
            LOGGER.debug(String.format("Invalid UUID '%s'", id));
            return false;
        }

        RUMUser currUser = userService.getOne(uid);
        if( currUser == null )
        {
            throw new RUMException(String.format("User with ID not found '%s'", uid));
        }

        RUMUserPassword.changePassword(currUser, pass,
                                        userService, radCheckService,
                                        appProps.getRumPasswordHashStr(),
                                        true);

        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/delete-user", method = RequestMethod.POST)
    public Boolean deleteUser(HttpServletRequest request,
                              @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        Boolean res = false;

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

        RUMUser currUser = userService.getOne(id);
        if( currUser == null )
        {
            LOGGER.debug(String.format("User with ID not found '%s'", id));
        }

        userService.deleteUser(currUser);
        userService.flush();

        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/delete-radcheck", method = RequestMethod.POST)
    public Boolean deleteRadCheck(HttpServletRequest request,
                              @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        Boolean res = false;

        Object idObj = model.get("id");

        if( idObj == null
                || Objects.toString(idObj) == null
                || StringUtils.isBlank(Objects.toString(idObj)) )
        {
            throw new RUMException("ID is missing.");
        }

        String idStr = Objects.toString(idObj);
        Integer id = null;

        try
        {
            id = Integer.parseInt(idStr);
        }
        catch( Exception ex )
        {
            LOGGER.debug(String.format("Invalid Integer '%s'", idStr), ex);
        }

        RUMRadCheck currRadCheck = radCheckService.getOne(id);
        if( currRadCheck == null )
        {
            LOGGER.debug(String.format("RadCheck with ID not found '%s'", id));
        }

        radCheckService.deleteRadCheck(currRadCheck);
        radCheckService.flush();

        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/create-user", method = RequestMethod.POST)
    public RUMUser createUser(HttpServletRequest request,
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
    @RequestMapping(value = "/api/admin/create-radcheck", method = RequestMethod.POST)
    public RUMRadCheck createRadCheck(HttpServletRequest request,
                              @RequestBody Map<String, Object> model) throws RUMException
    {
        LoggingUtils.logRequestDebug(request);

        RUMRadCheck res = null;

        res = radCheckService.createRadCheck(model);
        if( res != null )
        {
            radCheckService.saveAndFlush(res);
        }

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/admin-config/first", method = RequestMethod.GET)
    public AdminConfig adminConfigFirst(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        return adminConfigService.findFirstByOrderByLabel();
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/admin-config/{id}", method = RequestMethod.GET)
    public AdminConfig adminConfigByID(HttpServletRequest request,
                                       @PathVariable(value = "id")UUID id)
    {
        LoggingUtils.logRequestDebug(request);

        return adminConfigService.findById(id);
    }
}
