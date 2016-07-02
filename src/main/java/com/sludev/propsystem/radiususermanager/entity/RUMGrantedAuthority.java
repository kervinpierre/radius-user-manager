package com.sludev.propsystem.radiususermanager.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Granted Authorities on system users. This class controls the ROLE_* based
 * permissions in the system. It does NOT control ACL Permissions, which are
 * lighter and more fine grain.
 *
 * @author Kervin Pierre <info@sludev.com>
 *
 * Created by kervin on 2016-04-30.
 */
@Entity
@Audited
@Table(name = "rum_granted_authority")
public class RUMGrantedAuthority  extends AbstractAuditable<RUMUser, UUID>
        implements GrantedAuthority
{
    private static final Logger LOGGER = LogManager.getLogger(RUMGrantedAuthority.class);

    private UUID authId;
    private String authority;
    private RUMUser rumUser;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "auth_id", unique = true, columnDefinition = "BINARY(16)")
    public UUID getAuthId()
    {
        return authId;
    }

    public void setAuthId(UUID aid)
    {
        authId = aid;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public RUMUser getRumUser()
    {
        return rumUser;
    }

    public void setRumUser(RUMUser u)
    {
        rumUser = u;
    }

    @Override
    @Column(name = "authority", nullable = false)
    public String getAuthority()
    {
        return authority;
    }

    public void setAuthority(String auth)
    {
        this.authority = auth;
    }
}
