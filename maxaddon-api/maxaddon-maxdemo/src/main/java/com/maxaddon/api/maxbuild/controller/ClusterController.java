/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxbuild.repository.ClusterRepository;
import com.maxaddon.api.maxbuild.repository.ServerRepository;
import com.maxaddon.api.maxbuild.model.Cluster;
import com.maxaddon.api.maxbuild.payload.ClusterRequest;
import com.maxaddon.api.maxbuild.payload.ClusterResponse;
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
public class ClusterController {
    @Autowired
    private ClusterRepository clusterRepository;

    @Autowired
    private ServerRepository serverRepository;

    @GetMapping("/server/{serverId}/cluster/get")
    public Page<Cluster> getAllClustersByServerId(@PathVariable(value = "serverId") Long serverId,
            Pageable pageable) {
        return clusterRepository.findByServerId(serverId, pageable);
    }
    
    @PostMapping("/server/{serverId}/cluster/create")
    public Cluster createCluster(@PathVariable(value = "serverId") Long serverId,
            @Valid @RequestBody Cluster cluster) {
        return serverRepository.findById(serverId).map(server -> {
            cluster.setServer(server);
            return clusterRepository.save(cluster);
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + " not found"));
    }
  
    @PutMapping("/server/{serverId}/cluster/{clusterId}/edit")
    public Cluster updateCluster(@PathVariable(value = "serverId") Long serverId,
            @PathVariable(value = "clusterId") Long clusterId,
            @Valid @RequestBody Cluster clusterRequest) {
        if (!serverRepository.existsById(serverId)) {
            throw new ResourceNotFoundException("serverId " + serverId + " not found");
        }

        return clusterRepository.findById(clusterId).map(cluster -> {
            cluster.setName(clusterRequest.getName());
            cluster.setIdentification(clusterRequest.getIdentification());
            return clusterRepository.save(cluster);
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + "not found"));
    } 

    @DeleteMapping("/server/{serverId}/cluster/{clusterId}/delete")
    public ResponseEntity<?> deleteCluster(@PathVariable(value = "serverId") Long serverId,
            @PathVariable(value = "clusterId") Long clusterId) {
        if (!serverRepository.existsById(serverId)) {
            throw new ResourceNotFoundException("serverId " + serverId + " not found");
        }

        return clusterRepository.findById(clusterId).map(cluster -> {
            clusterRepository.delete(cluster);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + " not found"));
    }
    
    @GetMapping("/cluster/{clusterId}/get")
    public Cluster getClustersByClusterId(@PathVariable(value = "clusterId") Long clusterId,
            Pageable pageable) {
        if (!clusterRepository.existsById(clusterId)) {
            throw new ResourceNotFoundException("clusterId " + clusterId + " not found");
        }
        return clusterRepository.findById(clusterId).map(cluster -> {             
            return cluster;
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + "not found"));       
    }
    
    @GetMapping("/cluster/get")
    public List<ClusterResponse> getAllClusters() { 
        List<Cluster> clusters = clusterRepository.findAll();        
        List<ClusterResponse> clusterResponseList = new ArrayList<ClusterResponse>();
        for (int i=0; i<clusters.size(); i++) {
            ClusterResponse clusterResponse = new ClusterResponse();
            clusterResponse.setId(((Cluster)clusters.get(i)).getId());
            clusterResponse.setName(((Cluster)clusters.get(i)).getName());
            clusterResponse.setIdentification(((Cluster)clusters.get(i)).getIdentification());
            clusterResponse.setServerId(((Cluster)clusters.get(i)).getServer().getId());
            clusterResponse.setServerName(((Cluster)clusters.get(i)).getServer().getName());
            clusterResponse.setServerHost(((Cluster)clusters.get(i)).getServer().getHost());
            clusterResponseList.add(clusterResponse);
        } 
        return clusterResponseList;
        //return clusterRepository.findAll();
    }
    
    @PostMapping("/cluster/create")
    public Cluster createCluster(@Valid @RequestBody ClusterRequest cluster) {        
        return serverRepository.findById(cluster.getServerId()).map(server -> {
            cluster.setServer(server);
            Cluster dbCluster = new Cluster();
            dbCluster.setName(cluster.getName());
            dbCluster.setIdentification(cluster.getIdentification());
            dbCluster.setServer(server); 
            return clusterRepository.save(dbCluster);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Cluster"));          
    }
    
    @PutMapping("/cluster/{clusterId}/edit")
    public Cluster updateCluster(@PathVariable Long clusterId, @Valid @RequestBody ClusterRequest clusterRequest) {
        if (!clusterRepository.existsById(clusterId)) {
            throw new ResourceNotFoundException("clusterId " + clusterId + " not found");
        }        
        return clusterRepository.findById(clusterId).map(cluster -> {
            cluster.setName(clusterRequest.getName());
            cluster.setIdentification(clusterRequest.getIdentification());               
            serverRepository.findById(clusterRequest.getServerId()).map(server -> {
                cluster.setServer(server);                  
                return clusterRepository.save(cluster);
            }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Cluster"));                         
            return clusterRepository.save(cluster);
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + "not found"));        
    }
    
    @DeleteMapping("/cluster/{clusterId}/delete")
    public ResponseEntity<?> deleteCluster(@PathVariable Long clusterId) {        
        if (!clusterRepository.existsById(clusterId)) {
            throw new ResourceNotFoundException("clusterId " + clusterId + " not found");
        }        
        return clusterRepository.findById(clusterId).map(cluster -> {
            clusterRepository.delete(cluster);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + " not found"));
    }
    
}
