package com.chat.blog.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String content;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    public Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

}