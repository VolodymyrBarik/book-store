package com.example.bookstore.service.impls;

import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.service.ShoppingCartSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShoppingCartSupplierImpl implements ShoppingCartSupplier {
    private final ShoppingCartRepository repository;

    @Override
    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        return repository.save(shoppingCart);
    }
}
