package com.example.movie.service;

import com.example.movie.model.dto.UserDto;

import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Login dto optional.
     *
     * @param username the username
     * @param password the password
     * @return the optional
     */
    Optional<UserDto> loginDto(String username, String password);
}