package com.jpa.toyjpaproject.repository;

import com.jpa.toyjpaproject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PostRepositry:
 * JpaRepository를 상속하면 기본적인 CRUD 메서드가 자동으로 제공된다.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
