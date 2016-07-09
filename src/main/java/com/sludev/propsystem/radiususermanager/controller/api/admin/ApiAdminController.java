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
import com.sludev.propsystem.radiususermanager.util.RUMRadCheckSpecifications;
import com.sludev.propsystem.radiususermanager.util.RUMUserSpecifications;
import com.sludev.propsystem.radiususermanager.util.kendo.DatasourceVO;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        res.total = (long)res.data.length;

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
        res.total = (long)res.data.length;

        res.validate();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/read-all-usernames", method = RequestMethod.GET)
    public List<String> readAllUsernames(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        List<String> res = userService.findAllUsernames();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/read-all-radcheck-usernames", method = RequestMethod.GET)
    public List<String> readAllRCUsernames(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        List<String> res = radCheckService.findAllUsernames();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/read-all-firstnames", method = RequestMethod.GET)
    public List<String> readAllFirstnames(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        List<String> res = userService.findAllUsernames();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/read-all-lastnames", method = RequestMethod.GET)
    public List<String> readAllLastnames(HttpServletRequest request)
    {
        LoggingUtils.logRequestDebug(request);

        List<String> res = userService.findAllLastnames();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/read-all-users", method = RequestMethod.POST)
    public DatasourceVO readAllUsers(HttpServletRequest request,
                                     @RequestBody Map<String, Object> model)
    {
        LoggingUtils.logRequestDebug(request);

        DatasourceVO res = DatasourceVO.from();

        Object takeObj = model.get("take");
        Object skipObj = model.get("skip");
        Object pageObj = model.get("page");
        Object pageSizeObj = model.get("pageSize");

        Object sortObj = model.get("sort");
        Object filterObj = model.get("filter");

        Long take = null;
        Long skip = null;
        Integer page = null;
        Integer pageSize = null;

        Sort currSort = null;
        Sort currFilter = null;

        ArrayList sortList = null;

        Map filterExprs = null;

        Specification<RUMUser> currSpec = null;

        if( filterObj != null )
        {
            filterExprs = (Map)filterObj;

            String logic = (String)filterExprs.get("logic");
            ArrayList filtersList = (ArrayList)filterExprs.get("filters");
            List<String> opStack = Arrays.asList(new String[2*3]);

            for( int i=0; i<filtersList.size(); i++ )
            {
                Object obj = filtersList.get(i);

                opStack.set(i*3, ((Map)obj).get("field").toString());
                opStack.set(i*3+1, ((Map)obj).get("operator").toString());
                opStack.set(i*3+2, ((Map)obj).get("value").toString());
            }

            currSpec
                    = RUMUserSpecifications.runUserFilterQuery(logic, opStack.get(0),
                            opStack.get(1), opStack.get(2), opStack.get(3),
                            opStack.get(4), opStack.get(5));
        }

        if( sortObj != null )
        {
            sortList = (ArrayList)sortObj;
            Object searchObj = sortList.get(0);
            Map<String, Object> searchMap = (Map)searchObj;
            Sort.Direction currDir
                    = Sort.Direction.fromString(StringUtils.upperCase(
                    searchMap.get("dir").toString()));
            currSort = new Sort(currDir, searchMap.get("field").toString());
        }

        if( pageObj != null )
        {
            try
            {
                page = Integer.parseInt(pageObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'page' '%s'", pageObj), ex);
            }
        }

        if( pageSizeObj != null )
        {
            try
            {
                pageSize = Integer.parseInt(pageSizeObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'pageSize' '%s'", pageSizeObj), ex);
            }
        }

        if( takeObj != null )
        {
            try
            {
                take = Long.parseLong(takeObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'take' '%s'", takeObj), ex);
            }
        }

        if( skipObj != null )
        {
            try
            {
                skip = Long.parseLong(skipObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'skip' '%s'", skipObj), ex);
            }
        }

        if( page != null && pageSize != null )
        {
            PageRequest pr = new PageRequest(page-1, pageSize, currSort);
            Page<RUMUser> currPage = null;

            if( currSpec != null )
            {
                currPage = userService.findAllUsers(currSpec,pr);
            }
            else
            {
                currPage = userService.findAllUsers(pr);
            }

            res.data = currPage.getContent().toArray();
            res.total = currPage.getTotalElements();
        }
        else
        {
            res.data = userService.findAllUsers().toArray();
            res.total = (long)res.data.length;
        }

        res.validate();

        return res;
    }

    @ResponseBody
    @RequestMapping(value = "/api/admin/read-all-radchecks", method = RequestMethod.POST)
    public DatasourceVO readAllRadChecks(HttpServletRequest request,
                                         @RequestBody Map<String, Object> model)
    {
        LoggingUtils.logRequestDebug(request);

        DatasourceVO res = DatasourceVO.from();

        Object takeObj = model.get("take");
        Object skipObj = model.get("skip");
        Object pageObj = model.get("page");
        Object pageSizeObj = model.get("pageSize");

        Object sortObj = model.get("sort");
        Object filterObj = model.get("filter");

        Long take = null;
        Long skip = null;
        Integer page = null;
        Integer pageSize = null;

        Sort currSort = null;
        Sort currFilter = null;

        ArrayList sortList = null;

        Map filterExprs = null;

        Specification<RUMRadCheck> currSpec = null;

        if( filterObj != null )
        {
            filterExprs = (Map)filterObj;

            String logic = (String)filterExprs.get("logic");
            ArrayList filtersList = (ArrayList)filterExprs.get("filters");
            List<String> opStack = Arrays.asList(new String[2*3]);

            for( int i=0; i<filtersList.size(); i++ )
            {
                Object obj = filtersList.get(i);

                opStack.set(i*3, ((Map)obj).get("field").toString());
                opStack.set(i*3+1, ((Map)obj).get("operator").toString());
                opStack.set(i*3+2, ((Map)obj).get("value").toString());
            }

            currSpec
                    = RUMRadCheckSpecifications.runRadCheckFilterQuery(logic, opStack.get(0),
                    opStack.get(1), opStack.get(2), opStack.get(3),
                    opStack.get(4), opStack.get(5));
        }

        if( sortObj != null )
        {
            sortList = (ArrayList)sortObj;
            Object searchObj = sortList.get(0);
            Map<String, Object> searchMap = (Map)searchObj;
            Sort.Direction currDir
                    = Sort.Direction.fromString(StringUtils.upperCase(
                    searchMap.get("dir").toString()));
            currSort = new Sort(currDir, searchMap.get("field").toString());
        }

        if( pageObj != null )
        {
            try
            {
                page = Integer.parseInt(pageObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'page' '%s'", pageObj), ex);
            }
        }

        if( pageSizeObj != null )
        {
            try
            {
                pageSize = Integer.parseInt(pageSizeObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'pageSize' '%s'", pageSizeObj), ex);
            }
        }

        if( takeObj != null )
        {
            try
            {
                take = Long.parseLong(takeObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'take' '%s'", takeObj), ex);
            }
        }

        if( skipObj != null )
        {
            try
            {
                skip = Long.parseLong(skipObj.toString());
            }
            catch( Exception ex )
            {
                LOGGER.debug(String.format("Error parsing 'skip' '%s'", skipObj), ex);
            }
        }

        if( page != null && pageSize != null )
        {
            PageRequest pr = new PageRequest(page-1, pageSize, currSort);
            Page<RUMRadCheck> currPage = null;

            if( currSpec != null )
            {
                currPage = radCheckService.findAllRadChecks(currSpec,pr);
            }
            else
            {
                currPage = radCheckService.findAllRadChecks(pr);
            }

            res.data = currPage.getContent().toArray();
            res.total = currPage.getTotalElements();
        }
        else
        {
            res.data = radCheckService.findAllRadChecks().toArray();
            res.total = (long)res.data.length;
        }

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
