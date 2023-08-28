package com.example.bookstore.service;

import com.example.bookstore.dto.UserRegistrationRequestDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;

public interface AuthenticationService {
    //    UserLoginResponseDto login(UserLoginRequestDto request);

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
