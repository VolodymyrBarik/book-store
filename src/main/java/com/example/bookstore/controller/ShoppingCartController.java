package com.example.bookstore.controller;

import com.example.bookstore.dto.CartItemQuantityDto;
import com.example.bookstore.dto.CartItemRequestDto;
import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "ShoppingCart management", description = "Endpoints to manage shopping cart")
@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @Operation(summary = "Get a shopping cart",
            description = "Returns a shopping cart that belongs to certain user")
    @GetMapping
    public ShoppingCartResponseDto get(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.get(user);
    }

    @Operation(summary = "Adds book to shopping cart", description = "Adds book to shopping cart")
    @PostMapping
    public ShoppingCartResponseDto addBook(
            @RequestBody CartItemRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        cartItemService.create(requestDto, user);
        return shoppingCartService.get(user);
    }

    @Operation(summary = "Update the quantity",
            description = "Changes the quantity of the items that are already in shopping cart")
    @PutMapping("/cart-items/{cartItemId}")
    public ShoppingCartResponseDto update(@PathVariable Long cartItemId,
                                          @RequestBody CartItemQuantityDto dto,
                                          Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.update(user, cartItemId, dto.getQuantity());
    }

    @Operation(summary = "Delete from shopping cart",
            description = "Deletes items from shopping cart")
    @DeleteMapping("/cart-items/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long cartItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteCartItemFromShoppingCart(user, cartItemId);
    }
}
