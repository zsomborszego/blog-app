package com.chat.blog.api.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(@NotBlank String email, @NotBlank String password) {

}
