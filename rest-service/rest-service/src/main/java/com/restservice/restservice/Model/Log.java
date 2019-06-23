package com.restservice.restservice.Model;

import javax.persistence.*;

@Entity
public class Log{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "MessageType", nullable = false)
    private String MessageType;

    @Column(name = "MessageText", nullable = false)
    private String MessageText;

    @Column(name = "EntityName")
    private String EntityName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageType() {
        return MessageType;
    }

    public void setMessageType(String messageType) {
        MessageType = messageType;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }

    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }
}
