package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.UserLoginResponseDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserLoginResponseDto toUserLoginResponseDto(User user);

    UserResponseDto toResponseDto(User user);
}
