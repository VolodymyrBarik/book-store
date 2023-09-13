package com.example.bookstore.security;

import com.example.bookstore.dto.UserLoginRequestDto;
import com.example.bookstore.dto.UserLoginResponseDto;
import com.example.bookstore.dto.UserRegistrationRequestDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;

public interface AuthenticationService {
    UserLoginResponseDto authenticate(UserLoginRequestDto request);

    UserResponseDto register(UserRegistrationRequestDto requestDto) throws RegistrationException;
}
