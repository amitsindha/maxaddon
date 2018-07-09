/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.repository;

import com.maxaddon.api.maxbuild.model.Jvm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nodejs
 */
@Repository
public interface JvmRepository extends JpaRepository<Jvm, Long> {
    Page<Jvm> findByServerId(Long serverId, Pageable pageable);
    Page<Jvm> findByClusterId(Long clusterId, Pageable pageable);
}
