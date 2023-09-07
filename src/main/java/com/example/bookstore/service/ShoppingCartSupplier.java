package com.example.bookstore.service;

import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;

@FunctionalInterface
public interface ShoppingCartSupplier {
    ShoppingCart createShoppingCart(User user);
}
