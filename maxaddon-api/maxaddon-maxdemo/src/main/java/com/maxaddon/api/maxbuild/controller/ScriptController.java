/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxbuild.model.Cluster;
import com.maxaddon.api.maxbuild.model.Jvm;
import com.maxaddon.api.maxbuild.model.Script;
import com.maxaddon.api.maxbuild.model.ScriptType;
import com.maxaddon.api.maxbuild.payload.ScriptRequest;
import com.maxaddon.api.maxbuild.payload.ScriptResponse;
import com.maxaddon.api.maxbuild.repository.ClusterRepository;
import com.maxaddon.api.maxbuild.repository.JvmRepository;
import com.maxaddon.api.maxbuild.repository.ScriptRepository;
import com.maxaddon.api.maxbuild.repository.ScriptTypeRepository;
import com.maxaddon.api.maxbuild.repository.ServerRepository;
import com.maxaddon.api.maxsecurity.model.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author nodejs
 */
@RestController
@RequestMapping("/rest/api")
public class ScriptController {
    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private ServerRepository serverRepository;
    
    @Autowired
    private ScriptTypeRepository scriptTypeRepository;
    
    @Autowired
    private ClusterRepository clusterRepository;
    
    @Autowired
    private JvmRepository jvmRepository;
    
    public Cluster getCluster(Long clusterId) {
         return clusterRepository.findById(clusterId).map(cluster -> {             
            return cluster;
        }).orElseThrow(() -> new ResourceNotFoundException("clusterId " + clusterId + "not found"));     
    }
    
    public Jvm getJvm(Long jvmId) {
         return jvmRepository.findById(jvmId).map(jvm -> {             
            return jvm;
        }).orElseThrow(() -> new ResourceNotFoundException("jvmId " + jvmId + "not found"));     
    }
    
    public ScriptType getScriptType(Long scriptTypeId) {
         return scriptTypeRepository.findById(scriptTypeId).map(scriptType -> {             
            return scriptType;
        }).orElseThrow(() -> new ResourceNotFoundException("scriptTypeId " + scriptTypeId + "not found"));     
    }
    
    public Script getScript(Long scriptId) {
         return scriptRepository.findById(scriptId).map(script -> {             
            return script;
        }).orElseThrow(() -> new ResourceNotFoundException("scriptId " + scriptId + "not found"));     
    }
    
    @GetMapping("/script/{scriptId}/get")
    public Script getScriptsByScriptId(@PathVariable(value = "scriptId") Long scriptId,
            Pageable pageable) {
        if (!scriptRepository.existsById(scriptId)) {
            throw new ResourceNotFoundException("scriptId " + scriptId + " not found");
        }
        return scriptRepository.findById(scriptId).map(script -> {             
            return script;
        }).orElseThrow(() -> new ResourceNotFoundException("scriptId " + scriptId + "not found"));       
    }
    
    @PostMapping("/script/filter")
    public List<ScriptResponse> getFilterScripts(@Valid @RequestBody ScriptRequest scriptRequest) { 
        List<Script> scripts = null;        
        if(scriptRequest.getServerId() == null && 
                scriptRequest.getScriptTypeId() == null && 
                scriptRequest.getName() == null) {
            scripts = scriptRepository.findAll();     
        } else {
            scripts = scriptRepository.findByserverIdAndscriptTypeIdAndName(scriptRequest.getServerId(), 
                                                                    scriptRequest.getScriptTypeId(), 
                                                                    scriptRequest.getName());  
        }
        List<ScriptResponse> scriptResponseList = new ArrayList<ScriptResponse>();
        for (int i=0; i<scripts.size(); i++) {
            ScriptResponse scriptResponse = new ScriptResponse();
            scriptResponse.setId(((Script)scripts.get(i)).getId());
            scriptResponse.setName(((Script)scripts.get(i)).getName());
            scriptResponse.setDetails(((Script)scripts.get(i)).getDetails());
            scriptResponse.setContent(((Script)scripts.get(i)).getContent());
            scriptResponse.setIsInProgress(((Script)scripts.get(i)).getIsInProgress());
            scriptResponse.setServerId(((Script)scripts.get(i)).getServer().getId());
            scriptResponse.setServerName(((Script)scripts.get(i)).getServer().getName());            
            scriptResponse.setServerHost(((Script)scripts.get(i)).getServer().getHost());
            scriptResponse.setScriptTypeId(((Script)scripts.get(i)).getScriptType().getId());
            scriptResponse.setScriptTypeName(((Script)scripts.get(i)).getScriptType().getName());            
            scriptResponse.setClusterId(((Script)scripts.get(i)).getClusterId());
            scriptResponse.setJvmId(((Script)scripts.get(i)).getJvmId()); 
            scriptResponseList.add(scriptResponse);
        } 
        return scriptResponseList;     
    }
    
    
    @GetMapping("/script/get")
    public List<ScriptResponse> getAllScripts() { 
        List<Script> scripts = scriptRepository.findAll();        
        List<ScriptResponse> scriptResponseList = new ArrayList<ScriptResponse>();
        for (int i=0; i<scripts.size(); i++) {
            ScriptResponse scriptResponse = new ScriptResponse();
            scriptResponse.setId(((Script)scripts.get(i)).getId());
            scriptResponse.setName(((Script)scripts.get(i)).getName());
            scriptResponse.setDetails(((Script)scripts.get(i)).getDetails());
            scriptResponse.setContent(((Script)scripts.get(i)).getContent());
            scriptResponse.setIsInProgress(((Script)scripts.get(i)).getIsInProgress());
            scriptResponse.setServerId(((Script)scripts.get(i)).getServer().getId());
            scriptResponse.setServerName(((Script)scripts.get(i)).getServer().getName());
            scriptResponse.setServerHost(((Script)scripts.get(i)).getServer().getHost());
            scriptResponse.setScriptTypeId(((Script)scripts.get(i)).getScriptType().getId());
            scriptResponse.setScriptTypeName(((Script)scripts.get(i)).getScriptType().getName());            
            scriptResponse.setClusterId(((Script)scripts.get(i)).getClusterId());
            scriptResponse.setJvmId(((Script)scripts.get(i)).getJvmId());
            /*
            if(scripts.get(i).getClusterId() != null) {
                scriptResponse.setClusterName(getCluster(((Script)scripts.get(i)).getClusterId()).getName());
            }
            if(scripts.get(i).getJvmId() != null) {
                scriptResponse.setJvmName(getJvm(((Script)scripts.get(i)).getJvmId()).getName());
            }     
            */
            scriptResponseList.add(scriptResponse);
        } 
        return scriptResponseList;        
    }
    
    @PostMapping("/script/create")
    public Script createScript(@Valid @RequestBody ScriptRequest script) {        
        return serverRepository.findById(script.getServerId()).map(server -> {
            script.setServer(server);
            Script dbScript = new Script();
            dbScript.setName(script.getName());            
            dbScript.setServer(server);             
            dbScript.setIsInProgress(script.getIsInProgress());
            dbScript.setClusterId(script.getClusterId());
            dbScript.setJvmId(script.getJvmId());
            dbScript.setScriptType(getScriptType(script.getScriptTypeId()));                    
            return scriptRepository.save(dbScript);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Script"));          
    }
    
    @PutMapping("/script/{scriptId}/edit")
    public Script updateScript(@PathVariable Long scriptId, @Valid @RequestBody ScriptRequest scriptRequest) {
        if (!scriptRepository.existsById(scriptId)) {
            throw new ResourceNotFoundException("scriptId " + scriptId + " not found");
        }        
        return scriptRepository.findById(scriptId).map(script -> {
            script.setName(scriptRequest.getName());
            script.setDetails(scriptRequest.getDetails());
            script.setContent(scriptRequest.getContent());    
            script.setIsInProgress(scriptRequest.getIsInProgress());    
            script.setClusterId(script.getClusterId());
            script.setJvmId(script.getJvmId());
            
            serverRepository.findById(scriptRequest.getServerId()).map(server -> {                 
                script.setServer(server);                  
                script.setScriptType(getScriptType(scriptRequest.getScriptTypeId()));
                return scriptRepository.save(script);
            }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Jvm"));                         
            
            return scriptRepository.save(script);
        }).orElseThrow(() -> new ResourceNotFoundException("scriptId " + scriptId + "not found"));        
    }
    
    @PostMapping("/script/{scriptId}/execute")
    public void executeScript(@PathVariable Long scriptId, @Valid @RequestBody ScriptRequest scriptRequest) {         
        Script script = getScript(scriptId);
        script.setIsInProgress(Boolean.TRUE);
        scriptRepository.save(script);
        System.out.println("SCRIPT : "+scriptId+" NAME - "+scriptRequest.getName()+ " ON SERVER "+scriptRequest.getServerId()+" IS IN PROGRESS NOW.");
        performAction(scriptId, scriptRequest);                
    }
    
    /*
    public void callExecution(Long scriptId, ScriptRequest scriptRequest) {
        try {
            String uri = "http://localhost:8080/api/auth/signin";     
            User  user = new User("", "heeralthakore", "heeral@gmail.com", "heeral123");
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri, user, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode idNode = rootNode.path("accessToken");


            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Authorization", "Bearer " + idNode.asText());
            headers.add("Content-Type", "application/json");

            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpEntity<ScriptRequest> request = new HttpEntity<ScriptRequest>(scriptRequest, headers);
            restTemplate.postForObject("http://localhost:8080/rest/api/script/"+scriptId+"/action", request, String.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    */
    
    public void callExecution(Long scriptId, ScriptRequest scriptRequest) {
        try {
            String uri = "http://"+scriptRequest.getServerHost()+"/api/auth/signin";     
            User  user = new User("", "heeralthakore", "heeral@gmail.com", "heeral123");
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.postForObject(uri, user, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode idNode = rootNode.path("accessToken");


            MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
            headers.add("Authorization", "Bearer " + idNode.asText());
            headers.add("Content-Type", "application/json");

            restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpEntity<ScriptRequest> request = new HttpEntity<ScriptRequest>(scriptRequest, headers);
            restTemplate.postForObject("http://"+scriptRequest.getServerHost()+"/rest/api/script/"+scriptId+"/action", request, String.class);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostMapping("/script/{scriptId}/action")
    public void performAction(@PathVariable Long scriptId, @Valid @RequestBody ScriptRequest scriptRequest) {         
        Script script = getScript(scriptId);
        script.setIsInProgress(Boolean.FALSE);
        scriptRepository.save(script);
        System.out.println("SCRIPT : "+scriptId+" NAME - "+scriptRequest.getName()+ " ON SERVER "+scriptRequest.getServerId()+" IS COMPLTED.");
         
    }
    
    
    @DeleteMapping("/script/{scriptId}/delete")
    public ResponseEntity<?> deleteScript(@PathVariable Long scriptId) {        
        if (!scriptRepository.existsById(scriptId)) {
            throw new ResourceNotFoundException("scriptId " + scriptId + " not found");
        }        
        return scriptRepository.findById(scriptId).map(script -> {
            scriptRepository.delete(script);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("scriptId " + scriptId + " not found"));
    }
}
