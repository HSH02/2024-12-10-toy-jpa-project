package com.jpa.toyjpaproject.repository;

import com.jpa.toyjpaproject.domain.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  PostRepositoryTest:
 *  여기서는 PostRepository를 이용해 DB에 Post를 저장하고 조회하는 기능을 테스트하는 코드.
 */
@DataJpaTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("Post를 저장하고 불러오기")
    void testSaveAndFindPost(){
        // given
        // Post는 아직 없는 상태이므로, 잠시 뒤 만들 예정
        Post post = new Post(null, "Hello JPA", "This is my first post", false);


        // when
        post savedPost = postRepository.save(post);     // DB 저장

        // then
        // savedPost는 DB에서 생성된 ID를 가지게 될 것
        assertThat(savedPost.getId()).isNotNull();

        // 다시 findById로 불러와서 같은 title인지 확인
        Post foundPost = postRepository.findById(savedPost.getId()).orElseThrow();
        assertThat(foundPost.getTitle()).isEqualTo("Hello JPA");
    }
}