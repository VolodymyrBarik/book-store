package com.example.bookstore.service;

import com.example.bookstore.dto.response.ShoppingCartResponseDto;
import com.example.bookstore.model.User;

public interface ShoppingCartService {

    ShoppingCartResponseDto get(User user);

    ShoppingCartResponseDto update(User user, Long cartItemId, int quantity);

    void deleteCartItemFromShoppingCart(User user, Long cartItemId);

}
