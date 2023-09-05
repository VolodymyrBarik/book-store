package com.example.bookstore.controller;

import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;

    @GetMapping
    public ShoppingCartResponseDto get(Authentication authentication) {
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        return shoppingCartService.get(username);
    }

    @PostMapping
    public ShoppingCartResponseDto addBook(Long bookId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        cartItemService.create(bookId, userDetails);
        return shoppingCartService.get(((UserDetails) authentication.getPrincipal()).getUsername());
        //todo: update sc rep with query to receive it with cartItems
    }

    //    @PutMapping("/cart-items/{bookId}")
    //    public ShoppingCartResponseDto update(
    //    @PathVariable Long bookId, @RequestParam Long quantity) {
    //
    //    }
    //
    //    @DeleteMapping("/cart-items/{bookId}")
    //    public ShoppingCartResponseDto delete(
    //    @PathVariable Long bookId, @RequestParam Long quantity) {
    //
    //    }


    //    GET: /api/cart (Retrieve user's shopping cart)
    //    POST: /api/cart (Add book to the shopping cart)
    //    PUT: /api/cart/cart-items/{cartItemId} (Update quantity of a book in the shopping cart)
    //    DELETE: /api/cart/cart-items/{cartItemId} (

    //    Remove a book from the shopping cart)
    //    Send a POST request to /api/cart to add a book to the shopping cart.
    //    Send a GET request to /api/cart to retrieve my shopping cart.
    //    Send a DELETE request to /api/cart/books/{id} to remove a book from the shopping cart.
}
