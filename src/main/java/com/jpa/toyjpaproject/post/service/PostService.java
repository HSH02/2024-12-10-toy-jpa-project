package com.jpa.toyjpaproject.post.service;

import com.jpa.toyjpaproject.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Post post);
    Optional<Post> getPostById(Long id);
    List<Post> getAllPosts();
    Post updatePost(Long id, Post post);
    void deletePost(Long id);
}
