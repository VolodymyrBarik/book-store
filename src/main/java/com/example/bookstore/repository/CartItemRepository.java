package com.example.bookstore.repository;

import com.example.bookstore.model.CartItem;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findAllByShoppingCartId(Long id);

}
