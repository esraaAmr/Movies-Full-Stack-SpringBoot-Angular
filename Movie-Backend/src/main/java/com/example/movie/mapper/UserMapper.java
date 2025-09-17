package com.example.movie.mapper;

import com.example.movie.model.dto.UserDto;
import com.example.movie.model.entity.User;
import org.mapstruct.Mapper;

/**
 * The interface User mapper.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * To dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    UserDto toDto(User user);

    /**
     * To entity user.
     *
     * @param dto the dto
     * @return the user
     */
    User toEntity(UserDto dto);
}
