package com.example.bookstore.service.impls;

import com.example.bookstore.dto.request.CartItemRequestDto;
import com.example.bookstore.dto.response.ShoppingCartResponseDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.book.BookRepository;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public CartItem create(CartItemRequestDto requestDto, User user) {
        if (searchIfCartItemExist(requestDto, user).getId() != null) {
            return searchIfCartItemExist(requestDto, user);
        }
        Book bookFromDb = bookRepository.findById(requestDto.getBookId()).orElseThrow(
                () -> new EntityNotFoundException("Can't find book with id "
                        + requestDto.getBookId()));
        CartItem cartItem = searchIfCartItemExist(requestDto, user);
        cartItem.setBook(bookFromDb);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(user.getId());
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setQuantity(requestDto.getQuantity());
        return cartItemRepository.save(cartItem);
    }

    private CartItem searchIfCartItemExist(CartItemRequestDto requestDto, User user) {
        ShoppingCartResponseDto shoppingCartResponseDto =
                shoppingCartService.get(user);
        Optional<CartItem> byShoppingCartIdAndBookId =
                cartItemRepository.findByShoppingCartIdAndBook_id(
                shoppingCartResponseDto.getId(), requestDto.getBookId());
        if (byShoppingCartIdAndBookId.isPresent()) {
            CartItem cartItem = byShoppingCartIdAndBookId.get();
            cartItem.setQuantity(requestDto.getQuantity());
            return cartItemRepository.save(cartItem);
        }
        return new CartItem();
    }
}
