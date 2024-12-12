package com.jpa.toyjpaproject.comment.service;

import com.jpa.toyjpaproject.comment.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment createComment(Long postId, Comment comment);
    Optional<Comment> getComment(Long postId, Long commentId);
    List<Comment> getComments(Long postId);
    Comment updateComment(Long id, Comment comment);
    void deleteComment(Long id);
}
