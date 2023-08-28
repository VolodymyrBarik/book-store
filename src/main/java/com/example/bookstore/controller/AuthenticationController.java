package com.example.bookstore.controller;

import com.example.bookstore.dto.UserRegistrationRequestDto;
import com.example.bookstore.dto.UserResponseDto;
import com.example.bookstore.exception.RegistrationException;
import com.example.bookstore.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    //    @PostMapping("/login")
    //    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
    //        return authenticationService.login(request);
    //    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return authenticationService.register(requestDto);
    }
}
