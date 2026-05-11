package com.chat.blog;

import com.chat.blog.api.dto.CreatePostDTO;
import com.chat.blog.api.dto.CreateUserDTO;
import com.chat.blog.api.dto.PostsDTO;
import com.chat.blog.api.dto.UserCreatedDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class ApplicationTests {


    private static final String BASE_URL = "http://localhost:8080";

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Test
    void createUser_createPost_thenVerifyPostExists() throws Exception {
        var uniqueUserName = UUID.randomUUID().toString();
        var uniqueEmail = UUID.randomUUID() + "@example.com";

        // Create a new user
        var createUserDTO = new CreateUserDTO(uniqueUserName, uniqueEmail, "password123");

        var createUserResponse = createNewUser(createUserDTO);
        assertEquals(201, createUserResponse.statusCode());
        var createdUser = objectMapper.readValue(createUserResponse.body(), UserCreatedDTO.class);

        assertEquals(createdUser.username(), uniqueUserName);

        // Create a new POST
        var postTitle = "Első post" + UUID.randomUUID();
        var postContent = "Ez egy post" + UUID.randomUUID();

        var createPostDTO = new CreatePostDTO(createdUser.userId(), postTitle, postContent);
        var createdPostResponse = createPost(createPostDTO);

        assertEquals(201, createdPostResponse.statusCode());

        // Post exists
        var getPostsResponse = getPosts();

        assertEquals(200, getPostsResponse.statusCode());

        var postsDTO = objectMapper.readValue(getPostsResponse.body(), PostsDTO.class);

        assertTrue(postsDTO.postDTOS()
                .stream()
                .anyMatch(post ->
                        post.title().equals(postTitle)
                                && post.content().equals(postContent)
                                && post.username().equals(uniqueUserName)
                ));
    }

    @Test
    void can_create_user_when_username_missing() throws Exception {
        String username = null;
        var createUserDTO = new CreateUserDTO(username, "dummy@example.com", "password123");
        var createUserResponse = createNewUser(createUserDTO);
        assertEquals(400, createUserResponse.statusCode());
        assertTrue(createUserResponse
                .body()
                .contains("""
                        {"errors":{"username":"nem lehet üres"}"""));
    }


    private HttpResponse<String> createNewUser(CreateUserDTO createUserDTO) throws IOException, InterruptedException {
        var createUserRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(createUserDTO)
                ))
                .build();

        return client.send(
                createUserRequest,
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private HttpResponse<String> createPost(CreatePostDTO createPostDTO) throws IOException, InterruptedException {
        var createPostRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/post"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(createPostDTO)
                ))
                .build();

        return client.send(
                createPostRequest,
                HttpResponse.BodyHandlers.ofString()
        );
    }

    private HttpResponse<String> getPosts() throws IOException, InterruptedException {
        var getPostsRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/post"))
                .GET()
                .build();

        return client.send(
                getPostsRequest,
                HttpResponse.BodyHandlers.ofString()
        );
    }


}
