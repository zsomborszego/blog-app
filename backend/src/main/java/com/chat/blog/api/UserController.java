package com.chat.blog.api;

import com.chat.blog.api.dto.CreateUserDTO;
import com.chat.blog.api.dto.UserCreatedDTO;
import com.chat.blog.api.dto.UserLoggedInDTO;
import com.chat.blog.api.dto.UserLoginDTO;
import com.chat.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserCreatedDTO> createUser(@Valid @RequestBody CreateUserDTO request) {
        return ResponseEntity.status(CREATED).body(service.createUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoggedInDTO> login(@Valid @RequestBody UserLoginDTO request) {
        return ResponseEntity.status(OK).body(service.login(request));
    }

}
