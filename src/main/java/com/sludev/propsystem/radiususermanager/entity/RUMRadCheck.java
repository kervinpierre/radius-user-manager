package com.sludev.propsystem.radiususermanager.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by kervin on 2016-06-28.
 */
@Entity
@Audited
@Table(name = "radcheck", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class RUMRadCheck
{
    private static final Logger LOGGER = LogManager.getLogger(RUMRadCheck.class);

    private int id;
    private String  username;
    private String attribute;
    private String op;
    private String value;

    @Id
    @Column(name = "id", unique = true)
    private int getId()
    {
        return id;
    }

    private void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "username")
    private String getUsername()
    {
        return username;
    }

    private void setUsername(String username)
    {
        this.username = username;
    }

    @Column(name = "attribute")
    private String getAttribute()
    {
        return attribute;
    }

    private void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }

    @Column(name = "op")
    private String getOp()
    {
        return op;
    }

    private void setOp(String op)
    {
        this.op = op;
    }

    @Column(name = "value")
    private String getValue()
    {
        return value;
    }

    private void setValue(String value)
    {
        this.value = value;
    }
}
