package com.example.movie.mapper;

import com.example.movie.model.dto.UserDto;
import com.example.movie.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
