package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public record UserLoginResponseDto(String token) {

}
