package com.jpa.toyjpaproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/**
 * Post 엔티티
 * title, content, published 필드 보유
 * @Entity 어노테이션으로 JPA가 관리하는 엔티티로 지정
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;                // 기본 키

    @Column(nullable = false, length = 100)
    private String title;           // 제목

    @Lob
    private String content;         // 본문 내용

    private boolean published;       // 게시 여부

    @CreatedDate
    @Column(updatable = false)      // 생성일은 업데이트 안되게
    private LocalDate createdAt;

    @LastModifiedDate
    private LocalDate updatedAt;
}
