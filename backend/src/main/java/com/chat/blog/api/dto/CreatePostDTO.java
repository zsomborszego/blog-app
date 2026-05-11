package com.chat.blog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePostDTO(@NotNull Long userId, @NotBlank @Size(max = 255) String title, @NotBlank String content) {

}
