package com.chat.blog.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCommentDTO(@NotNull Long userId, @NotBlank String content) {

}
