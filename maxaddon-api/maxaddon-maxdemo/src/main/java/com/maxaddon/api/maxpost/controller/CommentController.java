/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxaddon.api.maxpost.controller;

import com.maxaddon.api.exception.ResourceNotFoundException;
import com.maxaddon.api.maxpost.model.Comment;
import com.maxaddon.api.maxpost.repository.CommentRepository;
import com.maxaddon.api.maxpost.repository.PostRepository;
import com.maxaddon.api.maxpost.payload.CommentRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/post/{postId}/comment/get")
    public Page<Comment> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId,
            Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable);
    }

    @PostMapping("/post/{postId}/comment/create")
    public Comment createComment(@PathVariable(value = "postId") Long postId,
            @Valid @RequestBody Comment comment) {
        return postRepository.findById(postId).map(post -> {
            comment.setPost(post);
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }
  
    @PutMapping("/post/{postId}/comment/{commentId}/edit")
    public Comment updateComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody Comment commentRequest) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
    } 

    @DeleteMapping("/post/{postId}/comment/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }

        return commentRepository.findById(commentId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
    }
    
    @GetMapping("/comment/{commentId}/get")
    public Comment getCommentsByCommentId(@PathVariable(value = "commentId") Long commentId,
            Pageable pageable) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("CommentId " + commentId + " not found");
        }
        return commentRepository.findById(commentId).map(comment -> {             
            return comment;
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));       
    }
    
    @GetMapping("/comment/get")
    public List<Comment> getAllComments() { 
        return commentRepository.findAll();
    }
    
    @PostMapping("/comment/create")
    public Comment createComment(@Valid @RequestBody CommentRequest comment) {        
        return postRepository.findById(comment.getPostId()).map(post -> {
            comment.setPost(post);
            Comment dbComment = new Comment();
            dbComment.setText(comment.getText());
            dbComment.setPost(post); 
            return commentRepository.save(dbComment);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Comment"));          
    }
    
    @PutMapping("/comment/{commentId}/edit")
    public Comment updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequest commentRequest) {
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("CommentId " + commentId + " not found");
        }
        /*        
        return postRepository.findById(comment.getPostId()).map(post -> { 
            comment.setPost(post);
            Comment dbComment = new Comment();
            dbComment.setText(comment.getText());
            dbComment.setPost(post); 
            return commentRepository.save(dbComment);
        }).orElseThrow(() -> new ResourceNotFoundException("Can not create Record for Comment"));   
        */
        
        return commentRepository.findById(commentId).map(comment -> {
            comment.setText(commentRequest.getText());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));        
    }
    
    @DeleteMapping("/comment/{commentId}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {        
        if (!commentRepository.existsById(commentId)) {
            throw new ResourceNotFoundException("CommentId " + commentId + " not found");
        }        
        return commentRepository.findById(commentId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + " not found"));
    }
    
}
