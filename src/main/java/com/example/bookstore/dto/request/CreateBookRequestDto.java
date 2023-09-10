package com.example.bookstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequestDto {
    @NotNull
    @NotEmpty
    private String title;
    @NotNull
    @NotEmpty
    private String author;
    @NotNull
    @NotEmpty
    private String isbn;
    @NotNull
    @Min(0)
    private BigDecimal price;
    private String description;
    private String coverImage;
    private Set<Long> categoriesId;
}
