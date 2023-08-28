package com.example.bookstore.dto;

import com.example.bookstore.model.Role;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String shippingAddress;
    private Set<Role> roles;
}
