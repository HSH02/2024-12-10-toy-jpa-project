package com.jpa.toyjpaproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpa.toyjpaproject.controller.PostController;
import com.jpa.toyjpaproject.domain.Post;
import com.jpa.toyjpaproject.error.GlobalExceptionHandler;
import com.jpa.toyjpaproject.error.PostNotFoundException;
import com.jpa.toyjpaproject.repository.PostRepository;
import com.jpa.toyjpaproject.service.PostService;
import com.jpa.toyjpaproject.web.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {


    private MockMvc mockMvc;

    @Mock
    private PostService postService;        // Mock 된 서비스

    @InjectMocks
    private PostController postController;  // 테스트 대상

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController)
                .setControllerAdvice(new GlobalExceptionHandler())      // 예외 핸들러
                .build();
    }

    @Test
    @DisplayName("POST /api/posts - 게시물 생성 성공")
    void testCreatePost_Success() throws Exception {
        // GIVEN
        PostDto postDto = PostDto.builder()
                .title("Test Post")
                .content("This is a test post.")
                .published(true)
                .build();

        Post savedPost = Post.builder()
                .id(1L)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .published(postDto.isPublished())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(postService.createPost(any(Post.class))).thenReturn(savedPost);     // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Post"))
                .andExpect(jsonPath("$.content").value("This is a test post."))
                .andExpect(jsonPath("$.published").value(true));

        verify(postService, times(1)).createPost(any(Post.class));       // 서비스 메서드 호출 검증
    }

    @Test
    @DisplayName("GET /api/posts/{id} - 존재하는 게시글 조회")
    void testGetPostById_Success() throws Exception {
        // GIVEN
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Existing Post")
                .content("Existing Content")
                .published(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(postService.getPostById(postId)).thenReturn(Optional.of(post));    // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(get("/api/posts/{id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("Existing Post"))
                .andExpect(jsonPath("$.content").value("Existing Content"))
                .andExpect(jsonPath("$.published").value(true));

        verify(postService, times(1)).getPostById(postId); // 서비스 메서드 호출 검증
    }

    @Test
    @DisplayName("GET /api/posts/{id} - 존재하지 않는 게시글 조회")
    void testGetPostById_NotFound() throws Exception {
        // GIVEN
        Long postId = 1L;
        when(postService.getPostById(postId)).thenReturn(Optional.empty());     // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(get("/api/posts/{id}", postId))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).getPostById(postId);      // 서비스 메서드 호출 검증
    }

    @Test
    @DisplayName("PUT /api/posts/{id} - 성공적으로 게시글 업데이트")
    void testUpdatePost_Success() throws Exception {
        // GIVEN
        Long postId = 1L;
        PostDto postDto = PostDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .published(false)
                .build();

        Post updatedPost = Post.builder()
                .id(postId)
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .published(postDto.isPublished())
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now())
                .build();

        when(postService.updatePost(eq(postId), any(Post.class))).thenReturn(updatedPost);  // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(put("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("Updated Title"))
                .andExpect(jsonPath("$.content").value("Updated Content"))
                .andExpect(jsonPath("$.published").value(false));

        verify(postService, times(1)).updatePost(eq(postId), any(Post.class)); // 서비스 메서드 호출 검증
    }

    @Test
    @DisplayName("PUT /api/posts/{id} - 존재하지 않는 게시글 업데이트 시도")
    void testUpdatePost_NotFound() throws Exception {
        // GIVEN
        Long postId = 1L;
        PostDto postDto = PostDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .published(false)
                .build();

        when(postService.updatePost(eq(postId), any(Post.class))).thenThrow(new PostNotFoundException(postId)); // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(put("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(postDto)))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).updatePost(eq(postId), any(Post.class)); // 서비스 메서드 호출 검증
    }

    @Test
    @DisplayName("DELETE /api/posts/{id} - 성공적으로 게시글 삭제")
    void testDeletePost_Success() throws Exception {
        // GIVEN
        Long postId = 1L;
        doNothing().when(postService).deletePost(postId); // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(delete("/api/posts/{id}", postId))
                .andExpect(status().isNoContent());

        verify(postService, times(1)).deletePost(postId); // 서비스 메서드 호출 검증
    }



    @Test
    @DisplayName("DELETE /api/posts/{id} - 존재하지 않는 게시글 삭제 시도")
    void testDeletePost_NotFound() throws Exception {
        // GIVEN
        Long postId = 1L;
        doThrow(new PostNotFoundException(postId)).when(postService).deletePost(postId); // Mock 서비스 동작 정의

        // WHEN & THEN
        mockMvc.perform(delete("/api/posts/{id}", postId))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).deletePost(postId); // 서비스 메서드 호출 검증
    }



}