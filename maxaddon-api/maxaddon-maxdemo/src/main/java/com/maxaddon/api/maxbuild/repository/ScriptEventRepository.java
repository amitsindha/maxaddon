/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.repository;

import com.maxaddon.api.maxbuild.model.ScriptEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nodejs
 */
@Repository
public interface ScriptEventRepository extends JpaRepository<ScriptEvent, Long> {
    
}
