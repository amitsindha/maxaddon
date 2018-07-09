/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.payload;

import com.maxaddon.api.maxbuild.model.ScriptType;
import com.maxaddon.api.maxbuild.model.Server;
import java.util.List;

/**
 *
 * @author nodejs
 */
public class ScriptResponse {
    private Long id;
    private String name;
    private String details;
    private String content;
    private Server server;
    private Long serverId;
    private ScriptType scriptType;
    private Long scriptTypeId;
    private String serverName;
    private String serverHost;
    private String scriptTypeName;
    private Long clusterId;
    private Long jvmId;
    private String clusterName;
    private String jvmName;   
    private Boolean isInProgress = Boolean.FALSE;
    private List<ScriptEventResponse> scriptEventResponse;

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

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public ScriptType getScriptType() {
        return scriptType;
    }

    public void setScriptType(ScriptType scriptType) {
        this.scriptType = scriptType;
    }

    public Long getScriptTypeId() {
        return scriptTypeId;
    }

    public void setScriptTypeId(Long scriptTypeId) {
        this.scriptTypeId = scriptTypeId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getScriptTypeName() {
        return scriptTypeName;
    }

    public void setScriptTypeName(String scriptTypeName) {
        this.scriptTypeName = scriptTypeName;
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

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }
    
    public Boolean getIsInProgress() {
        return isInProgress;
    }

    public void setIsInProgress(Boolean isInProgress) {
        this.isInProgress = isInProgress;
    }  

    public List<ScriptEventResponse> getScriptEventResponse() {
        return scriptEventResponse;
    }

    public void setScriptEventResponse(List<ScriptEventResponse> scriptEventResponse) {
        this.scriptEventResponse = scriptEventResponse;
    }
    
}
