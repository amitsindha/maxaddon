/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxbuild.controller;

import com.maxaddon.api.maxbuild.repository.MemberRepository;
import com.maxaddon.api.maxbuild.service.MemberService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.maxaddon.api.maxbuild.model.Member;
import com.maxaddon.api.maxsecurity.repository.UserRepository;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author nodejs
 */

@RestController
@RequestMapping("/api/members")
public class MemberController {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private MemberService memberService;
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    List<Member> findAll() {
        System.out.println("GET ALL MEMBER CALLING ");
        return memberService.findAll();
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional <Member> get(@PathVariable Long id) {
        System.out.println("FIND BY ID MEMBER CALLING "+id+" ... ");
        return memberService.find(id);
    }

    
    @RequestMapping(value = "/find/*", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional <Member> get(@RequestParam String name) {
        return memberService.findByName(name);
    }
    
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> create(@RequestBody Member member) {
        System.out.println("CREATE MEMBER CALLING ");
        memberService.save(member);
        /*
        HttpHeaders headers = new HttpHeaders();
        ControllerLinkBuilder linkBuilder = linkTo(methodOn(MemberController.class).get(member.getId()));
        headers.setLocation(linkBuilder.toUri());
        */
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@RequestBody Member member) {
        System.out.println("UPDATE MEMBER CALLING ");
        memberService.save(member);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        System.out.println("DELETE MEMBER CALLING ");
        memberService.delete(id);
    }    
    
}
