/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxbuild.model.ScriptType;
import com.maxaddon.api.maxbuild.payload.ScriptTypeResponse;
import com.maxaddon.api.maxbuild.repository.ScriptTypeRepository;
import com.maxaddon.api.maxbuild.repository.ServerRepository;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/rest/api/scripttype")
public class ScriptTypeController {
    @Autowired
    ScriptTypeRepository scriptTypeRepository;
    
    @GetMapping("/get")
    public List<ScriptType> getAllScriptTypes() {
        return scriptTypeRepository.findAll();
    }
    
    @GetMapping("/get/custom")
    public List<ScriptTypeResponse> getCustomScriptTypes() {
        List<ScriptType> scriptTypeList = scriptTypeRepository.findAll();
        List<ScriptTypeResponse> scriptTypeResponseList = new ArrayList<ScriptTypeResponse>();
        for(int i=0 ;i<scriptTypeList.size(); i++) {
            if(scriptTypeList.get(i).getIsCustom()) {
                ScriptTypeResponse scriptTypeResponse = new ScriptTypeResponse();
                scriptTypeResponse.setId(scriptTypeList.get(i).getId());
                scriptTypeResponse.setName(scriptTypeList.get(i).getName());
                scriptTypeResponse.setDetails(scriptTypeList.get(i).getDetails());
                scriptTypeResponse.setIsCustom(scriptTypeList.get(i).getIsCustom());
                scriptTypeResponseList.add(scriptTypeResponse);
            }
        }
        return scriptTypeResponseList;
        //return scriptTypeRepository.findAll();
    }
    
    @GetMapping("/get/default")
    public List<ScriptTypeResponse> getDefaultScriptTypes() {
        List<ScriptType> scriptTypeList = scriptTypeRepository.findAll();
        List<ScriptTypeResponse> scriptTypeResponseList = new ArrayList<ScriptTypeResponse>();
        for(int i=0 ;i<scriptTypeList.size(); i++) {
            if(!scriptTypeList.get(i).getIsCustom()) {
                ScriptTypeResponse scriptTypeResponse = new ScriptTypeResponse();
                scriptTypeResponse.setId(scriptTypeList.get(i).getId());
                scriptTypeResponse.setName(scriptTypeList.get(i).getName());
                scriptTypeResponse.setDetails(scriptTypeList.get(i).getDetails());
                scriptTypeResponse.setIsCustom(scriptTypeList.get(i).getIsCustom());
                scriptTypeResponseList.add(scriptTypeResponse);
            }
        }
        return scriptTypeResponseList;
        //return scriptTypeRepository.findAll();
    }
    
    @PostMapping("/create")
    public ScriptType createScriptType(@Valid @RequestBody ScriptType scriptType) {
        return scriptTypeRepository.save(scriptType);
    }

    @PutMapping("/edit/{scriptTypeId}")
    public ScriptType updateScriptType(@PathVariable Long scriptTypeId, @Valid @RequestBody ScriptType scriptTypeRequest) {
        return scriptTypeRepository.findById(scriptTypeId).map(scriptType -> {            
            scriptType.setName(scriptTypeRequest.getName());
            scriptType.setDetails(scriptTypeRequest.getDetails());
            scriptType.setIsCustom(scriptTypeRequest.getIsCustom()); 
            return scriptTypeRepository.save(scriptType);
        }).orElseThrow(() -> new ResourceNotFoundException("scriptTypeId " + scriptTypeId + " not found"));
    }

    @DeleteMapping("/delete/{scriptTypeId}")
    public ResponseEntity<?> deleteScriptType(@PathVariable Long scriptTypeId) {
        return scriptTypeRepository.findById(scriptTypeId).map(scriptType -> {
            scriptTypeRepository.delete(scriptType);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("scriptTypeId " + scriptTypeId + " not found"));
    }
}
