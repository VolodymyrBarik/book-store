package com.example.bookstore.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
}
