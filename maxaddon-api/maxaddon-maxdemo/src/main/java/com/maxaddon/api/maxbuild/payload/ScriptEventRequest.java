/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.payload;

import com.maxaddon.api.maxbuild.model.Script;
import com.maxaddon.api.maxbuild.model.ScriptType;
import com.maxaddon.api.maxbuild.model.Server;

/**
 *
 * @author nodejs
 */
public class ScriptEventRequest {
    private Long id;
    private String log; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

     
    
}
