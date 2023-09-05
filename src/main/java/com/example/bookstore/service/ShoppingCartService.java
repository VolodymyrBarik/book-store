package com.example.bookstore.service;

import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;

public interface ShoppingCartService {

    public ShoppingCartResponseDto get(String username);

    public ShoppingCart createShoppingCart(User user);

}
