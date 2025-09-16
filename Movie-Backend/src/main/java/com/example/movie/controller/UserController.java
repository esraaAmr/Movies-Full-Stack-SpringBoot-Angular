package com.example.movie.controller;

import com.example.movie.model.dto.UserDto;
import com.example.movie.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Endpoints for user authentication and management")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Login with username and password")
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestParam String username, @RequestParam String password) {
        UserDto userDto = userService.loginDto(username, password).get();
        return ResponseEntity.ok(userDto);
    }
}
