package com.jpa.toyjpaproject.controller;

import com.jpa.toyjpaproject.domain.Comment;
import com.jpa.toyjpaproject.service.CommentService;
import com.jpa.toyjpaproject.error.CommentNotFoundException;
import com.jpa.toyjpaproject.web.dto.CommentDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class ConmmentController {

    private final CommentService commentService;

    @Autowired
    public ConmmentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @Valid @RequestBody CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .visible(commentDto.isVisible())
                .build();
        Comment createdComment = commentService.createComment(postId, comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long postId, @PathVariable Long id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        return comment.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long postId, @PathVariable Long id, @Valid @RequestBody CommentDto commentDto) {
        Comment updatedComment = Comment.builder()
                .content(commentDto.getContent())
                .visible(commentDto.isVisible())
                .build();

        try {
            Comment savedComment = commentService.updateComment(id, updatedComment);
            return ResponseEntity.ok(savedComment);
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (CommentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
