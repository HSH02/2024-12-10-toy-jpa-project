package com.jpa.toyjpaproject.service;

import com.jpa.toyjpaproject.domain.Post;
import com.jpa.toyjpaproject.error.PostNotFoundException;
import com.jpa.toyjpaproject.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post updatePost(Long id, Post updatePost) {
        return postRepository.findById(id).map(post -> {
            post.setTitle(updatePost.getTitle());
            post.setContent(updatePost.getContent());
            post.setPublished(updatePost.isPublished());
            return postRepository.save(post);
        }).orElseThrow(() -> new PostNotFoundException(id));
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
