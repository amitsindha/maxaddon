/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxbuild.model.Cluster;
import com.maxaddon.api.maxbuild.model.Server;
import com.maxaddon.api.maxbuild.model.Jvm;
import com.maxaddon.api.maxbuild.payload.ClusterRequest;
import com.maxaddon.api.maxbuild.payload.ClusterResponse;
import com.maxaddon.api.maxbuild.payload.JvmRequest;
import com.maxaddon.api.maxbuild.payload.JvmResponse;
import com.maxaddon.api.maxbuild.repository.ClusterRepository;
import com.maxaddon.api.maxbuild.repository.JvmRepository;
import com.maxaddon.api.maxbuild.repository.ServerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
public class JvmController {
    @Autowired
    private JvmRepository jvmRepository;

    @Autowired
    private ServerRepository serverRepository;
    
    @Autowired
    private ClusterRepository clusterRepository;
    
    public Cluster getCluster(Long clusterId) {
         return clusterRepository.findById(clusterId).map(cluster -> {             
            return cluster;
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + "not found"));     
    }
    
    public Server getServer(Long serverId) {
         return serverRepository.findById(serverId).map(server -> {             
            return server;
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + "not found"));     
    }
        
    @GetMapping("/server/{serverId}/jvm/get")
    public Page<Jvm> getAllJvmsByServerId(@PathVariable(value = "serverId") Long serverId,
            Pageable pageable) {
        return jvmRepository.findByServerId(serverId, pageable);
    }
    
    @PostMapping("/server/{serverId}/jvm/create")
    public Jvm createJvm(@PathVariable(value = "serverId") Long serverId,
            @Valid @RequestBody Jvm jvm) {
        return serverRepository.findById(serverId).map(server -> {
            jvm.setServer(server);
            return jvmRepository.save(jvm);
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + " not found"));
    }
  
    @PutMapping("/server/{serverId}/jvm/{jvmId}/edit")
    public Jvm updateJvm(@PathVariable(value = "serverId") Long serverId,
            @PathVariable(value = "jvmId") Long jvmId,
            @Valid @RequestBody Jvm jvmRequest) {
        if (!serverRepository.existsById(serverId)) {
            throw new ResourceNotFoundException("serverId " + serverId + " not found");
        }

        return jvmRepository.findById(jvmId).map(jvm -> {
            jvm.setName(jvmRequest.getName()); 
            Server server = getServer(serverId);
            jvm.setServer(server);
            return jvmRepository.save(jvm);
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + "not found"));
    } 

    @DeleteMapping("/server/{serverId}/jvm/{jvmId}/delete")
    public ResponseEntity<?> deleteJvm(@PathVariable(value = "serverId") Long serverId,
            @PathVariable(value = "jvmId") Long jvmId) {
        if (!serverRepository.existsById(serverId)) {
            throw new ResourceNotFoundException("serverId " + serverId + " not found");
        }

        return jvmRepository.findById(jvmId).map(jvm -> {
            jvmRepository.delete(jvm);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + " not found"));
    }
    
    @GetMapping("/jvm/{jvmId}/get")
    public Jvm getJvmsByJvmId(@PathVariable(value = "jvmId") Long jvmId,
            Pageable pageable) {
        if (!jvmRepository.existsById(jvmId)) {
            throw new ResourceNotFoundException("jvmId " + jvmId + " not found");
        }
        return jvmRepository.findById(jvmId).map(jvm -> {             
            return jvm;
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + "not found"));       
    }
    
    @GetMapping("/jvm/get")
    public List<JvmResponse> getAllJvms() { 
        List<Jvm> jvms = jvmRepository.findAll();        
        List<JvmResponse> jvmResponseList = new ArrayList<JvmResponse>();
        for (int i=0; i<jvms.size(); i++) {
            JvmResponse jvmResponse = new JvmResponse();
            jvmResponse.setId(((Jvm)jvms.get(i)).getId());
            jvmResponse.setName(((Jvm)jvms.get(i)).getName());
            jvmResponse.setServerId(((Jvm)jvms.get(i)).getServer().getId());
            jvmResponse.setServerName(((Jvm)jvms.get(i)).getServer().getName());
            jvmResponse.setServerHost(((Jvm)jvms.get(i)).getServer().getHost());
            
            if(((Jvm)jvms.get(i)).getCluster() != null) {
                jvmResponse.setClusterId(((Jvm)jvms.get(i)).getCluster().getId());
                jvmResponse.setClusterName(((Jvm)jvms.get(i)).getCluster().getName());
                jvmResponse.setClusterIdentification(((Jvm)jvms.get(i)).getCluster().getIdentification());            
            } else {
                jvmResponse.setClusterId(new Long(0));
                jvmResponse.setClusterName("");
                jvmResponse.setClusterIdentification("");            
            }
            
            jvmResponseList.add(jvmResponse);
        } 
        return jvmResponseList;        
    }
    
    @PostMapping("/jvm/create")
    public Jvm createJvm(@Valid @RequestBody JvmRequest jvm) {        
        return serverRepository.findById(jvm.getServerId()).map(server -> {
            jvm.setServer(server);
            Jvm dbJvm = new Jvm();
            dbJvm.setName(jvm.getName());            
            dbJvm.setServer(server); 
            Cluster cluster = getCluster(jvm.getClusterId());
            if(cluster != null) {
                dbJvm.setCluster(cluster);
            }        
            return jvmRepository.save(dbJvm);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Jvm"));          
    }
    
    @PutMapping("/jvm/{jvmId}/edit")
    public Jvm updateJvm(@PathVariable Long jvmId, @Valid @RequestBody JvmRequest jvmRequest) {
        if (!jvmRepository.existsById(jvmId)) {
            throw new ResourceNotFoundException("jvmId " + jvmId + " not found");
        }        
        return jvmRepository.findById(jvmId).map(jvm -> {
            jvm.setName(jvmRequest.getName());
            
            serverRepository.findById(jvmRequest.getServerId()).map(server -> {
                Cluster cluster = getCluster(jvmRequest.getClusterId());
                if(cluster != null) {
                    jvm.setCluster(cluster);
                }   
                jvm.setServer(server);                  
                return jvmRepository.save(jvm);
            }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Jvm"));                         
            
            return jvmRepository.save(jvm);
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + "not found"));        
    }
    
    @DeleteMapping("/jvm/{jvmId}/delete")
    public ResponseEntity<?> deleteJvm(@PathVariable Long jvmId) {        
        if (!jvmRepository.existsById(jvmId)) {
            throw new ResourceNotFoundException("jvmId " + jvmId + " not found");
        }        
        return jvmRepository.findById(jvmId).map(jvm -> {
            jvmRepository.delete(jvm);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + " not found"));
    }
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping("/cluster/{clusterId}/jvm/get")
    public Page<Jvm> getAllJvmsByClusterId(@PathVariable(value = "clusterId") Long clusterId,
            Pageable pageable) {
        return jvmRepository.findByServerId(clusterId, pageable);
    }
    
    @PostMapping("/cluster/{clusterId}/jvm/create")
    public Jvm createJvmByCluster(@PathVariable(value = "clusterId") Long clusterId,
            @Valid @RequestBody Jvm jvm) {
        return clusterRepository.findById(clusterId).map(cluster -> {
            jvm.setCluster(cluster);
            return jvmRepository.save(jvm);
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + " not found"));
    }
  
    @PutMapping("/cluster/{clusterId}/jvm/{jvmId}/edit")
    public Jvm updateJvmByCluster(@PathVariable(value = "clusterId") Long clusterId,
            @PathVariable(value = "jvmId") Long jvmId,
            @Valid @RequestBody Jvm jvmRequest) {
        if (!clusterRepository.existsById(clusterId)) {
            throw new ResourceNotFoundException("clusterId " + clusterId + " not found");
        }

        return jvmRepository.findById(jvmId).map(jvm -> {
            jvm.setName(jvmRequest.getName()); 
            Cluster cluster = getCluster(clusterId);
            if(cluster != null) {
                jvm.setCluster(cluster);
            }            
            return jvmRepository.save(jvm);
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + "not found"));
    } 

    @DeleteMapping("/cluster/{clusterId}/jvm/{jvmId}/delete")
    public ResponseEntity<?> deleteJvmByCluster(@PathVariable(value = "clusterId") Long clusterId,
            @PathVariable(value = "jvmId") Long jvmId) {
        if (!clusterRepository.existsById(clusterId)) {
            throw new ResourceNotFoundException("clusterId " + clusterId + " not found");
        }

        return jvmRepository.findById(jvmId).map(jvm -> {
            jvmRepository.delete(jvm);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + " not found"));
    }
    
}
