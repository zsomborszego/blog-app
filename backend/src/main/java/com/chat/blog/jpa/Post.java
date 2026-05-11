package com.chat.blog.jpa;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    public String content;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    public List<Comment> comments = new ArrayList<>();

}