package com.chat.blog.service;

import com.chat.blog.api.dto.CreateCommentDTO;
import com.chat.blog.api.dto.CreatePostDTO;
import com.chat.blog.api.dto.PostsDTO;
import com.chat.blog.jpa.Comment;
import com.chat.blog.jpa.CommentRepository;
import com.chat.blog.jpa.Post;
import com.chat.blog.jpa.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BlogService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    public BlogService(PostRepository postRepository, CommentRepository commentRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
    }

    public void createPost(CreatePostDTO request) {
        var user = userService.getById(request.userId());

        var entity = new Post();
        entity.title = request.title();
        entity.content = request.content();
        entity.user = user;
        entity.createdAt = LocalDateTime.now();

        postRepository.save(entity);
    }

    public void addComment(Long postId, CreateCommentDTO request) {
        var user = userService.getById(request.userId());
        var post = getPost(postId);

        var entity = new Comment();
        entity.content = request.content();
        entity.user = user;
        entity.post = post;
        entity.createdAt = LocalDateTime.now();

        commentRepository.save(entity);
    }

    public PostsDTO getAllPosts() {
        var entities = postRepository.findAll();

        var postDTOs = entities.stream().map(post -> {
            var comments = post.comments.stream().map(comment ->
                    new PostsDTO.CommentDTO(comment.content, comment.user.username, comment.createdAt)).toList();
            return new PostsDTO.PostDTO(post.id, post.user.username, post.title, post.content, comments, post.createdAt);
        }).toList();

        return new PostsDTO(postDTOs);
    }

    private Post getPost(Long id) {
        var entity = postRepository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Post not exist");
        }
        return entity.get();
    }
}
