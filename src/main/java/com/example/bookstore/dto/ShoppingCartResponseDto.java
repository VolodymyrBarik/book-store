package com.example.bookstore.dto;

import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShoppingCartResponseDto {
    private Long id;
    private User user;
    private Set<CartItem> cartItems;
}
