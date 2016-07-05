package com.sludev.propsystem.radiususermanager.service.impl;

import com.sludev.propsystem.radiususermanager.entity.RUMRadCheck;
import com.sludev.propsystem.radiususermanager.repository.RUMRadCheckRepository;
import com.sludev.propsystem.radiususermanager.service.RUMRadCheckService;
import com.sludev.propsystem.radiususermanager.util.RUMException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * * The actual implementation of the user Service layer interface.
 *
 * @author Kervin Pierre <info@sludev.com>
 *     
 * Created by kervin on 2016-04-30.
 */
@Service
public class RUMRadCheckServiceImpl implements RUMRadCheckService
{
    private static final Logger LOGGER = LogManager.getLogger(RUMRadCheckService.class);

    @Autowired(required = true)
    private RUMRadCheckRepository radCheckRepository;

    @Override
    public RUMRadCheck findByUsername(String userName)
    {
        return radCheckRepository.findByUsername(userName);
    }

    public RUMRadCheck getOne(int id)
    {
        return radCheckRepository.getOne(id);
    }

//    @Override
//    public RUMRadCheck validateCredential(String username, String password)
//    {
//        return radCheckRepository.findByUsernameAndPassword(username, password);
//    }

    @Override
    public List<RUMRadCheck> findAllRadCheck()
    {
        return radCheckRepository.findAll();
    }

    @Override
    public RUMRadCheck createRadCheck(final Map<String, Object> model) throws RUMException
    {
        RUMRadCheck res = new RUMRadCheck();

        if( model == null )
        {
            return null;
        }

        Object idObj = model.get("id");

        if( idObj != null
                && Objects.toString(idObj) != null
                && StringUtils.isNoneBlank(Objects.toString(idObj)) )
        {
            throw new RUMException(
                    String.format("ID should not be present '%s'",
                            model.get("id")));
        }

        updateRadCheck(res, model);

        return res;
    }

    public RUMRadCheck updateRadCheck(final Map<String, Object> model) throws RUMException
    {
        RUMRadCheck res = null;

        if( model == null )
        {
            return null;
        }

        updateRadCheck(res, model);

        return res;
    }

    @Override
    public void updateRadCheck(final RUMRadCheck user, final Map<String, Object> model) throws RUMException
    {
        Object usernameObj = model.get("username");
        Object attributeObj = model.get("attribute");
        Object opObj    = model.get("op");
        Object valueObj = model.get("value");

        String username = null;
        String attribute = null;
        String op = null;
        String value = null;

        username  = StringUtils.trim(Objects.toString(usernameObj, null));
        attribute = StringUtils.trim(Objects.toString(attributeObj, null));
        op        = StringUtils.trim(Objects.toString(opObj, null));
        value     = StringUtils.trim(Objects.toString(valueObj, null));

        if( StringUtils.isNoneBlank(username))
        {
            user.setUsername(username);
        }

        if( StringUtils.isNoneBlank(attribute))
        {
            user.setAttribute(attribute);
        }

        if( StringUtils.isNoneBlank(op))
        {
            user.setOp(op);
        }

        if( value != null )
        {
            user.setValue(value);
        }
    }

    @Override
    public RUMRadCheck createRadCheck(String username)
    {
        RUMRadCheck res = new RUMRadCheck();

        res.setUsername(username);

        return res;
    }

    @Override
    public RUMRadCheck saveAndFlush(RUMRadCheck RUMRadCheck)
    {
        RUMRadCheck res = radCheckRepository.saveAndFlush(RUMRadCheck);

        return res;
    }

    @Override
    public void flush()
    {
        radCheckRepository.flush();
    }

    @Override
    public void updateRadCheck(RUMRadCheck RUMRadCheck)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteRadCheck(RUMRadCheck RUMRadCheck)
    {
        radCheckRepository.delete(RUMRadCheck);
    }
}
