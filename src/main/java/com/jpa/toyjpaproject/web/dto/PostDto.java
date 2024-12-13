// src/main/java/com/jpa/toyjpaproject/web/dto/PostDto.java
package com.jpa.toyjpaproject.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;

    @NotEmpty(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotEmpty(message = "Content is required")
    private String content;

    private boolean published;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
