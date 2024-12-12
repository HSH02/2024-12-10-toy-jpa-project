package com.jpa.toyjpaproject.post.repository;

import com.jpa.toyjpaproject.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * PostRepositry:
 * JpaRepository를 상속하면 기본적인 CRUD 메서드가 자동으로 제공된다.
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
