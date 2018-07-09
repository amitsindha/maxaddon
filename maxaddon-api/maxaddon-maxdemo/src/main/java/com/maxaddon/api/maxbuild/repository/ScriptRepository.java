/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.repository;

import com.maxaddon.api.maxbuild.model.Script;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nodejs
 */
@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    Page<Script> findByServerId(Long serverId, Pageable pageable);
    Page<Script> findByScriptTypeId(Long scriptTypeId, Pageable pageable);
    
    @Query("SELECT s FROM Script s where s.server.id = :serverId or s.scriptType.id = :scriptTypeId or s.name = :name")
    List<Script> findByserverIdAndscriptTypeIdAndName(@Param("serverId") Long serverId, @Param("scriptTypeId") Long scriptTypeId, @Param("name") String name);
    
}
