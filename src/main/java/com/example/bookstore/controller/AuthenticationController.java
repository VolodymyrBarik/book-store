package com.example.bookstore.controller;

import com.example.bookstore.dto.request.UserLoginRequestDto;
import com.example.bookstore.dto.request.UserRegistrationRequestDto;
import com.example.bookstore.dto.response.UserLoginResponseDto;
import com.example.bookstore.dto.response.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.security.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication management", description = "Endpoints to register and authentication")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login", description = "Login into the system")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }

    @Operation(summary = "Register", description = "Register in the system")
    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return authenticationService.register(requestDto);
    }
}
