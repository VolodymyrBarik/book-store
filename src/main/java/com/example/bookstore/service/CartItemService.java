package com.example.bookstore.service;

import com.example.bookstore.model.CartItem;
import org.springframework.security.core.userdetails.UserDetails;

public interface CartItemService {
    CartItem create(Long bookId, UserDetails userDetails);
}
