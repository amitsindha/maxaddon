/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxbuild.model.Application;
import com.maxaddon.api.maxbuild.payload.ApplicationRequest;
import com.maxaddon.api.maxbuild.payload.ApplicationResponse;
import com.maxaddon.api.maxbuild.payload.ClusterRequest;
import com.maxaddon.api.maxbuild.payload.ClusterResponse;
import com.maxaddon.api.maxbuild.repository.ApplicationRepository;
import com.maxaddon.api.maxbuild.repository.ServerRepository;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nodejs
 */
@RestController
@RequestMapping("/rest/api")
public class ApplicationController {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ServerRepository serverRepository;

    @GetMapping("/server/{serverId}/application/get")
    public Page<Application> getAllApplicationsByServerId(@PathVariable(value = "serverId") Long serverId,
            Pageable pageable) {
        return applicationRepository.findByServerId(serverId, pageable);
    }
    
    @PostMapping("/server/{serverId}/application/create")
    public Application createApplication(@PathVariable(value = "serverId") Long serverId,
            @Valid @RequestBody Application application) {
        return serverRepository.findById(serverId).map(server -> {
            application.setServer(server);
            return applicationRepository.save(application);
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + " not found"));
    }
  
    @PutMapping("/server/{serverId}/application/{applicationId}/edit")
    public Application updateApplication(@PathVariable(value = "serverId") Long serverId,
            @PathVariable(value = "applicationId") Long applicationId,
            @Valid @RequestBody Application applicationRequest) {
        if (!serverRepository.existsById(serverId)) {
            throw new ResourceNotFoundException("serverId " + serverId + " not found");
        }

        return applicationRepository.findById(applicationId).map(application -> {
            application.setName(applicationRequest.getName());
            application.setDetails(applicationRequest.getDetails());
            return applicationRepository.save(application);
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + "not found"));
    } 

    @DeleteMapping("/server/{serverId}/application/{applicationId}/delete")
    public ResponseEntity<?> deleteCluster(@PathVariable(value = "serverId") Long serverId,
            @PathVariable(value = "applicationId") Long applicationId) {
        if (!serverRepository.existsById(serverId)) {
            throw new ResourceNotFoundException("serverId " + serverId + " not found");
        }

        return applicationRepository.findById(applicationId).map(application -> {
            applicationRepository.delete(application);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("applicationId " + applicationId + " not found"));
    }
    
    @GetMapping("/application/{applicationId}/get")
    public Application getApplicationsByApplicationId(@PathVariable(value = "applicationId") Long applicationId,
            Pageable pageable) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("applicationId " + applicationId + " not found");
        }
        return applicationRepository.findById(applicationId).map(application -> {             
            return application;
        }).orElseThrow(() -> new ResourceNotFoundException("applicationId " + applicationId + "not found"));       
    }
    
    @GetMapping("/application/get")
    public List<ApplicationResponse> getAllApplications() { 
        List<Application> applications = applicationRepository.findAll();        
        List<ApplicationResponse> applicationResponseList = new ArrayList<ApplicationResponse>();
        for (int i=0; i<applications.size(); i++) {
            ApplicationResponse applicationResponse = new ApplicationResponse();
            applicationResponse.setId(((Application)applications.get(i)).getId());
            applicationResponse.setName(((Application)applications.get(i)).getName());
            applicationResponse.setDetails(((Application)applications.get(i)).getDetails());
            applicationResponse.setServerId(((Application)applications.get(i)).getServer().getId());
            applicationResponse.setServerName(((Application)applications.get(i)).getServer().getName());
            applicationResponse.setServerHost(((Application)applications.get(i)).getServer().getHost());
            applicationResponseList.add(applicationResponse);
        } 
        return applicationResponseList;        
    }
    
    @PostMapping("/application/create")
    public Application createApplication(@Valid @RequestBody ApplicationRequest application) {        
        return serverRepository.findById(application.getServerId()).map(server -> {
            application.setServer(server);
            Application dbApplication = new Application();
            dbApplication.setName(application.getName());
            dbApplication.setDetails(application.getDetails());
            dbApplication.setServer(server); 
            return applicationRepository.save(dbApplication);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Cluster"));          
    }
    
    @PutMapping("/application/{applicationId}/edit")
    public Application updateApplication(@PathVariable Long applicationId, @Valid @RequestBody ApplicationRequest applicationRequest) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("applicationId " + applicationId + " not found");
        }        
        return applicationRepository.findById(applicationId).map(application -> {
            application.setName(applicationRequest.getName());
            application.setDetails(applicationRequest.getDetails());
            serverRepository.findById(applicationRequest.getServerId()).map(server -> {
                application.setServer(server);                  
                return applicationRepository.save(application);
            }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Application"));                         
            return applicationRepository.save(application);
        }).orElseThrow(() -> new ResourceNotFoundException("applicationId " + applicationId + "not found"));        
    }
    
    @DeleteMapping("/application/{applicationId}/delete")
    public ResponseEntity<?> deleteApplication(@PathVariable Long applicationId) {        
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("applicationId " + applicationId + " not found");
        }        
        return applicationRepository.findById(applicationId).map(application -> {
            applicationRepository.delete(application);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("applicationId " + applicationId + " not found"));
    }
}
