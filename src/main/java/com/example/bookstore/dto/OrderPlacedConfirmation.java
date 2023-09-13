package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderPlacedConfirmation {
    private Long orderId;
    private String message;
}
