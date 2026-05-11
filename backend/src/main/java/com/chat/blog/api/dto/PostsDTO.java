package com.chat.blog.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PostsDTO(List<PostDTO> postDTOS) {

    public record PostDTO(Long id, String username, String title, String content, List<CommentDTO> commentDTOS,
                          LocalDateTime createdAt) {
    }

    public record CommentDTO(String content, String userName, LocalDateTime createdAt) {
    }

}
