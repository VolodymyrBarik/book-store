package com.example.bookstore.service;

import com.example.bookstore.dto.CartItemRequestDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;

public interface CartItemService {
    CartItem create(CartItemRequestDto requestDto, User user);
}
