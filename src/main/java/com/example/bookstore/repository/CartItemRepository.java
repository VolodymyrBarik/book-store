package com.example.bookstore.repository;

import com.example.bookstore.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByShoppingCartIdAndBook_id(Long bookId, Long shoppingCartId);

    Optional<CartItem> findByIdAndShoppingCartId(Long Id, Long shoppingCartId);

    //void deleteByIdAndShoppingCartId(Long cartItemId, Long shoppingCartId);
}
