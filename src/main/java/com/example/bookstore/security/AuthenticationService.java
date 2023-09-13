package com.example.bookstore.security;

import com.example.bookstore.dto.request.UserLoginRequestDto;
import com.example.bookstore.dto.request.UserRegistrationRequestDto;
import com.example.bookstore.dto.response.UserLoginResponseDto;
import com.example.bookstore.dto.response.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
