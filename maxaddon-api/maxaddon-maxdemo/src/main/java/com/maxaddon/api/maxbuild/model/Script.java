/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maxaddon.api.maxsecurity.model.audit.UserDateAudit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 *
 * @author nodejs
 */
@Entity
@Table(name = "scripts")
public class Script extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", nullable = true)
    private String name;

    @Size(max = 100)
    @Column(name = "details", nullable = true)
    private String details;

    @Lob
    @Column(name = "content", nullable = true)
    private String content;
    
    @Column(name = "cluster_id", nullable = true)
    private Long clusterId;
    
    @Column(name = "jvm_id", nullable = true)
    private Long jvmId;
    
    @Column(name = "isinprogress",columnDefinition = "tinyint(1) default 0")
    private Boolean isInProgress = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(Boolean isInProgress) {
        this.isInProgress = isInProgress;
    }       
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "server_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Server server;
    
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    } 
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scripttype_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ScriptType scriptType;
    
    public ScriptType getScriptType() {
        return scriptType;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    } 

    public Long getClusterId() {
        return clusterId;
    }

    public void setClusterId(Long clusterId) {
        this.clusterId = clusterId;
    }

    public Long getJvmId() {
        return jvmId;
    }

    public void setJvmId(Long jvmId) {
        this.jvmId = jvmId;
    }
    
    
}
