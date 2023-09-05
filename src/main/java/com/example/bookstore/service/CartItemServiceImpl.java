package com.example.bookstore.service;

import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem create(Long bookId, UserDetails userDetails) {
        Book bookFromDb = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Can't find book with id " + bookId));
        User userFromDb = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        CartItem cartItem = new CartItem();
        cartItem.setBook(bookFromDb);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(userFromDb.getId());
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(1);
        return cartItemRepository.save(cartItem);
    }
}
