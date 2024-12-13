package com.jpa.toyjpaproject.service;

import com.jpa.toyjpaproject.domain.Post;
import com.jpa.toyjpaproject.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;      // MOCK 리포지터리

    @InjectMocks
    private PostServiceImpl postService;        // 테스트 대상 서비스

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);     // Mock 초기화
    }

    @Test
    @DisplayName("Post 생성 테스트")
    void createPost() {
        // given
        Post post = Post.builder()
                .title("Test Post")
                .content("Test Content")
                .published(false)
                .build();

        Post savedPost = Post.builder()
                .id(1L)
                .title("Test Post")
                .content("Test Content")
                .published(false)
                .build();

        when(postRepository.save(post)).thenReturn(savedPost);

        // when
        Post result = postService.createPost(post);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Test Post");
        verify(postRepository, times(1)).save(post);
    }

    @Test
    @DisplayName("Post 조회 테스트")
    void getPostById() {
        // given
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Test Post")
                .content("Test Content")
                .published(false)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // when
        Optional<Post> result = postService.getPostById(postId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(postId);
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    @DisplayName("모든 Post 조회 테스트")
    void getAllPosts() {
        // given
        Post post1 = Post.builder()
                .id(1L)
                .title("First Post")
                .content("First Content")
                .published(true)
                .build();

        Post post2 = Post.builder()
                .id(2L)
                .title("Second Post")
                .content("Second Content")
                .published(false)
                .build();

        List<Post> mockPosts = Arrays.asList(post1, post2);

        when(postRepository.findAll()).thenReturn(mockPosts);   // Mock 동작 정의

        // when
        //List<Post> result = postService.getAllPosts();

        // then
//        assertThat(result).hasSize(2);
//        assertThat(result).contains(post1, post2);
//        verify(postRepository, times(1)).findAll();     // 메서드 호출 검증
    }

    @Test
    @DisplayName("Post 수정 테스트")
    void updatePost() {
        // given
        Long postId = 1L;
        Post existingPost = Post.builder()
                .id(postId)
                .title("Old Title")
                .content("Old Content")
                .published(false)
                .build();

        Post updatedPost = Post.builder()
                .title("New Title")
                .content("New Content")
                .published(true)
                .build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(existingPost)).thenReturn(updatedPost);

        // when
        Post result = postService.updatePost(postId, updatedPost);

        // then
        assertThat(result.getTitle()).isEqualTo("New Title");
        assertThat(result.getContent()).isEqualTo("New Content");
        assertThat(result.isPublished()).isTrue();
        verify(postRepository, times(1)).findById(postId);
        verify(postRepository, times(1)).save(existingPost);
    }

    @Test
    @DisplayName("Post 삭제 테스트")
    void deletePost() {
        // given
        Long postId = 1L;

        // when
        postService.deletePost(postId);
        // then
        verify(postRepository, times(1)).deleteById(postId);
    }
}