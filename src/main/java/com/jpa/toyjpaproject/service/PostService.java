package com.jpa.toyjpaproject.service;

import com.jpa.toyjpaproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostService {
    Post createPost(Post post);
    Optional<Post> getPostById(Long id);
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
    Page<Post> getAllPosts(Pageable pageable);
}
