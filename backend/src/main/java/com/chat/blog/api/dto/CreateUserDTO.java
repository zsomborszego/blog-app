package com.chat.blog.api.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDTO(@NotBlank String username, @NotBlank String email, @NotBlank String password) {

}
