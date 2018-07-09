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
@Table(name = "members")
public class Member extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "NAME", nullable = true)
    private String name;
    
    @Column(name = "AGE", nullable = true)
    private int age;
    
    @Size(max = 50)
    @Column(name = "EMAIL", nullable = true)
    private String email;
    
    @Size(max = 50)
    @Column(name = "COMPANY", nullable = true)
    private String company;
    
    @Size(max = 10)
    @Column(name = "PHONE", nullable = true)
    private String phone;
    
    @Size(max = 100)
    @Column(name = "ADDRESS", nullable = true)
    private String address;
    
    @Column(name = "BALANCE", nullable = true)
    private int balance;
    
    @Column(name = "ISACTIVE",columnDefinition = "tinyint(1) default 0")
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    
}
