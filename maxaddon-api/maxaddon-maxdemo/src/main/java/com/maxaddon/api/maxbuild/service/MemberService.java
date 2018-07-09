/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.service;

import com.maxaddon.api.maxbuild.repository.MemberRepository;
import com.maxaddon.api.maxsecurity.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxaddon.api.maxbuild.model.Member;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author nodejs
 */

@Service
public class MemberService { 
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
    
    //@Transactional(readOnly = true)
    //@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
    public List<Member> findAll() {
        return memberRepository.findAll();
    }
    
    //@Transactional(readOnly = true)
    //@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
    public Optional <Member> find(Long id) {
        return memberRepository.findById(id);
    }

    
    //@Transactional(readOnly = true)
    //@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
    public Optional <Member> findByName(String name) {
        return memberRepository.findByName(name);
    } 
    
    
    //@Transactional
    //@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
    public Member save(Member member) {
        return memberRepository.save(member);
    }
   
    //@Transactional
    //@PreAuthorize("hasAuthority('ROLE_USER') and hasAuthority('ROLE_ADMIN')")
    public void delete(Long id) {
        memberRepository.deleteById(id);
    } 
    
}
