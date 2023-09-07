package com.example.bookstore.service;

import com.example.bookstore.dto.CartItemRequestDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface CartItemService {
    CartItem create(CartItemRequestDto requestDto, User user);
}
