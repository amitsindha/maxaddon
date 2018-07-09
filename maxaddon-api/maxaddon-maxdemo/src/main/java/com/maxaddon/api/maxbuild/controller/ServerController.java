/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxbuild.model.Server;
import com.maxaddon.api.maxbuild.payload.ServerRequest;
import com.maxaddon.api.maxbuild.repository.ServerRepository;
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
 * @author Amit Sindha<amit.sindha@gmail.com>
 */

@RestController
@RequestMapping("/rest/api/server")
public class ServerController {
    
    @Autowired
    ServerRepository serverRepository;

    /*
    @GetMapping("/get")
    public Page<Server> getAllServers(Pageable pageable) {
        return serverRepository.findAll(pageable);
    }
    */
    
    @GetMapping("/get")
    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }
    
    @PostMapping("/create")
    public Server createServer(@Valid @RequestBody Server server) {
        return serverRepository.save(server);
    }

    @PutMapping("/edit/{serverId}")
    public Server updateServer(@PathVariable Long serverId, @Valid @RequestBody Server serverRequest) {
        return serverRepository.findById(serverId).map(server -> {            
            server.setName(serverRequest.getName());
            server.setHost(serverRequest.getHost());
            server.setIp(serverRequest.getIp());
            server.setOs(serverRequest.getOs());
            server.setIsVM(serverRequest.getIsVM());
            server.setCore(serverRequest.getCore());
            server.setHdd(serverRequest.getHdd());
            server.setRam(serverRequest.getRam()); 
            server.setIsActive(serverRequest.getIsActive()); 
            return serverRepository.save(server);
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + " not found"));
    }

    @DeleteMapping("/delete/{serverId}")
    public ResponseEntity<?> deleteServer(@PathVariable Long serverId) {
        return serverRepository.findById(serverId).map(server -> {
            serverRepository.delete(server);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("serverId " + serverId + " not found"));
    }
}
