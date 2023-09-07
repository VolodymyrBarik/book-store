package com.example.bookstore.controller;

import com.example.bookstore.dto.CartItemQuantityDto;
import com.example.bookstore.dto.CartItemRequestDto;
import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @GetMapping
    public ShoppingCartResponseDto get(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.get(user);
    }

    @PostMapping
    public ShoppingCartResponseDto addBook(@RequestBody CartItemRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        cartItemService.create(requestDto, user);
        return shoppingCartService.get(user);
    }

    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartResponseDto update(@PathVariable Long cartItemId, @RequestBody CartItemQuantityDto dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.update(user, cartItemId, dto.getQuantity());
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItemFromShoppingCart(user, cartItemId);
    }
}
