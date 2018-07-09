/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.repository;

import com.maxaddon.api.maxbuild.model.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nodejs
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);        
}
