package com.chat.blog.service;


import com.chat.blog.api.dto.*;
import com.chat.blog.jpa.User;
import com.chat.blog.jpa.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserCreatedDTO createUser(CreateUserDTO request) {
        if (repository.existsByUsernameOrEmail(request.username(), request.email())) {
            throw new EntityExistsException("Username or Email already in used");
        }
        var entity = new User();
        entity.username = request.username();
        entity.password = request.password();
        entity.email = request.email();
        entity.createdAt = LocalDateTime.now();

        entity = repository.save(entity);

        return new UserCreatedDTO(entity.username, entity.id);
    }

    public UserLoggedInDTO login(UserLoginDTO request) {
        var entity = repository.findByEmailAndPassword(request.email(), request.password());
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Uset not exist");
        }
        return new UserLoggedInDTO(entity.get().id, entity.get().username);
    }

    public User getById(Long id) {
        var entity = repository.findById(id);
        if (entity.isEmpty()) {
            throw new EntityNotFoundException("Uset not exist");
        }
        return entity.get();
    }
}
