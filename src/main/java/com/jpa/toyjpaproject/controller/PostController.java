package com.jpa.toyjpaproject.controller;

import com.jpa.toyjpaproject.domain.Post;
import com.jpa.toyjpaproject.error.PostNotFoundException;
import com.jpa.toyjpaproject.service.PostService;
import com.jpa.toyjpaproject.web.dto.PostDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // POST 생성
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .published(postDto.isPublished())
                .build();
        Post createdPost = postService.createPost(post);

        PostDto responseDto = PostDto.builder()
                .id(createdPost.getId())
                .title(createdPost.getTitle())
                .content(createdPost.getContent())
                .published(createdPost.isPublished())
                .createdAt(createdPost.getCreatedAt())
                .updatedAt(createdPost.getUpdatedAt())
                .build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    // GET 특정 Post 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        Optional<Post> postOpt = postService.getPostById(id);
        return postOpt.map(post -> {
            PostDto postDto = PostDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .published(post.isPublished())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .build();
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // PUT Post 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) {
        Post updatedPost = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .published(postDto.isPublished())
                .build();

        try {
            Post savedPost = postService.updatePost(id, updatedPost);
            PostDto responseDto = PostDto.builder()
                    .id(savedPost.getId())
                    .title(savedPost.getTitle())
                    .content(savedPost.getContent())
                    .published(savedPost.isPublished())
                    .createdAt(savedPost.getCreatedAt())
                    .updatedAt(savedPost.getUpdatedAt())
                    .build();
            return ResponseEntity.ok(responseDto);
        } catch (PostNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE Post 삭제
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (PostNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
