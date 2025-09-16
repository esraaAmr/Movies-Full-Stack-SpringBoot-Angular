package com.example.movie.service;

import com.example.movie.error.exception.AuthenticationFailedException;
import com.example.movie.mapper.UserMapper;
import com.example.movie.model.dto.UserDto;
import com.example.movie.model.entity.User;
import com.example.movie.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDto> loginDto(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new AuthenticationFailedException("Invalid username or password");
        }

        User user = userOptional.get();
        // Use PasswordEncoder to compare hashed passwords
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationFailedException("Invalid username or password");
        }

        return Optional.of(userMapper.toDto(user));
    }
}