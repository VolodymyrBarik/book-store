package com.example.bookstore.service.impls;

import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.ShoppingCartSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartSupplier supplier;
    private final CartItemRepository cartItemRepository;

    @Override
    public ShoppingCartResponseDto get(User user) {
        ShoppingCart shoppingCart = findOrCreateNewShoppingCart(user);
        shoppingCart.setUser(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto update(User user, Long cartItemId, int quantity) {
        ShoppingCart shoppingCart = findOrCreateNewShoppingCart(user);
        CartItem cartItem = cartItemRepository.
                findByIdAndShoppingCartId(cartItemId, shoppingCart.getId()).get();
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return shoppingCartMapper.toDto(findOrCreateNewShoppingCart(user));
    }


    public void deleteCartItemFromShoppingCart(User user, Long cartItemId) {
        ShoppingCart shoppingCart = findOrCreateNewShoppingCart(user);
        CartItem cartItemToBeDeleted = cartItemRepository.
                findByIdAndShoppingCartId(cartItemId, shoppingCart.getId()).get();
        cartItemRepository.delete(cartItemToBeDeleted);
    }

    private ShoppingCart findOrCreateNewShoppingCart(User user) {
        return shoppingCartRepository.findShoppingCartByUser_Id(user.getId())
                .orElseGet(() -> supplier.createShoppingCart(user));
    }
}
