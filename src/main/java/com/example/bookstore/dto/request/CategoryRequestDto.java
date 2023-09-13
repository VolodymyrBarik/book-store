package com.example.bookstore.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
