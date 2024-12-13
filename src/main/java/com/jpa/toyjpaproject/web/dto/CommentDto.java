package com.jpa.toyjpaproject.web.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    @NotEmpty(message = "Content is required")
    private String content;

    private boolean visible;
}
