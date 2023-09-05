package com.example.bookstore.service;

import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    public ShoppingCartResponseDto get(String username) {
        User user = userRepository.findByEmail(username).get();
        Set<CartItem> allItemsBelongsToShoppingCart = getAllItemsBelongsToShoppingCart(user.getId());
        ShoppingCart shoppingCart = createShoppingCart(user);
        shoppingCart.setCartItems(allItemsBelongsToShoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    public ShoppingCart createShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCart.setId(user.getId());
        return shoppingCartRepository.save(shoppingCart);
    }

    private Set<CartItem> getAllItemsBelongsToShoppingCart(Long id) {
        List<CartItem> allByShoppingCartId =
                cartItemRepository.findAllByShoppingCartId(id);
        return new HashSet<>(allByShoppingCartId);
    }
}
