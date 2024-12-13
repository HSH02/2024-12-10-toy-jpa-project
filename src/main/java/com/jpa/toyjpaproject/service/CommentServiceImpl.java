package com.jpa.toyjpaproject.service;

import com.jpa.toyjpaproject.domain.Comment;
import com.jpa.toyjpaproject.repository.CommentRepository;
import com.jpa.toyjpaproject.error.CommentNotFoundException;
import com.jpa.toyjpaproject.error.PostNotFoundException;
import com.jpa.toyjpaproject.domain.Post;
import com.jpa.toyjpaproject.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    @Transactional
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        post.addComment(comment);
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Override
    @Transactional
    public Comment updateComment(Long id, Comment updateComment) {
        return commentRepository.findById(id).map(comment -> {
            comment.setContent(updateComment.getContent());
            comment.setVisible(updateComment.isVisible());
            return commentRepository.save(comment);
        }).orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        if(!commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }
        commentRepository.deleteById(id);
    }
}
