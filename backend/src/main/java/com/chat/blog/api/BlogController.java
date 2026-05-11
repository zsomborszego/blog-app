package com.chat.blog.api;

import com.chat.blog.api.dto.CreateCommentDTO;
import com.chat.blog.api.dto.CreatePostDTO;
import com.chat.blog.api.dto.PostsDTO;
import com.chat.blog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/post")
public class BlogController  {

    private final BlogService service;

    public BlogController(BlogService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody CreatePostDTO request) {
        service.createPost(request);
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PostsDTO> getAllPosts() {
        var posts = service.getAllPosts();
        return ResponseEntity.status(OK).body(posts);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long postId, @Valid @RequestBody CreateCommentDTO request) {
        service.addComment(postId, request);
        return ResponseEntity.status(CREATED).build();
    }

}
