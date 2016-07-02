package com.sludev.propsystem.radiususermanager.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.AbstractAuditable;

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
 * Created by kervin on 2016-04-30.
 */

@Entity
@Audited
@Table(name = "rum_user_security_qa")
public class RUMUserSecurityQA extends AbstractAuditable<RUMUserSecurityQA, Long>
{
    private static final Logger LOGGER = LogManager.getLogger(RUMUserSecurityQA.class);

    private UUID qaId;
    private RUMUser rumUser;
    private String question;
    private String answer;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "qa_id", unique = true, columnDefinition = "BINARY(16)")
    public UUID getQaId()
    {
        return qaId;
    }

    public void setQaId(UUID uid)
    {
        qaId = uid;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ruser_id")
    public RUMUser getRumUser()
    {
        return rumUser;
    }

    public void setRumUser(RUMUser RUMUser)
    {
        this.rumUser = RUMUser;
    }

    @Column(name = "question")
    public String getQuestion()
    {
        return question;
    }

    public void setQuestion(String question)
    {
        this.question = question;
    }

    @Column(name = "answer")
    public String getAnswer()
    {
        return answer;
    }

    public void setAnswer(String answer)
    {
        this.answer = answer;
    }
}
