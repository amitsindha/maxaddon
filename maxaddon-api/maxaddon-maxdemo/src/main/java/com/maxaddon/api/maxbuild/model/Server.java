/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.model;

import com.maxaddon.api.maxsecurity.model.audit.UserDateAudit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author nodejs
 */
@Entity
@Table(name = "servers")
public class Server extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "host", nullable = true)
    private String host;
    
    @Size(max = 15)
    @Column(name = "ip", nullable = true)
    private String ip;
    
    @Size(max = 50)
    @Column(name = "os", nullable = true)
    private String os;
    
    @Column(name = "isvm",columnDefinition = "tinyint(1) default 0")
    private Boolean isVM = Boolean.FALSE;
    
    @Column(name = "core", nullable = true)
    private int core;
    
    @Size(max = 15)
    @Column(name = "ram", nullable = true)
    private String ram;
    
    @Size(max = 15)
    @Column(name = "hdd", nullable = true)
    private String hdd;
    
    @Column(name = "isactive",columnDefinition = "tinyint(1) default 0")
    private Boolean isActive = Boolean.FALSE;
    
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Boolean getIsVM() {
        return isVM;
    }

    public void setIsVM(Boolean isVM) {
        this.isVM = isVM;
    }

    public int getCore() {
        return core;
    }

    public void setCore(int core) {
        this.core = core;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    } 
    
}
