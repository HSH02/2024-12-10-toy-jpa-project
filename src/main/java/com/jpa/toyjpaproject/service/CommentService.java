package com.jpa.toyjpaproject.service;

import com.jpa.toyjpaproject.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Long postId, Comment comment);
    Optional<Comment> getCommentById(Long id);
    List<Comment> getCommentsByPostId(Long postId);
    Comment updateComment(Long id, Comment updatedComment);
    void deleteComment(Long id);
}
