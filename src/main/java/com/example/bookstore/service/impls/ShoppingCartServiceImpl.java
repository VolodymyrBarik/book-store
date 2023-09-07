package com.example.bookstore.service.impls;

import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.ShoppingCartSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartSupplier supplier;

    @Override
    public ShoppingCartResponseDto get(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(
                () -> new EntityNotFoundException("Can't find user " + username));
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(user.getId())
                .orElseGet(() -> supplier.createShoppingCart(user));
        shoppingCart.setUser(user);
        return shoppingCartMapper.toDto(shoppingCart);
    }

//    public ShoppingCart createShoppingCart(User user) {
//        ShoppingCart shoppingCart = supplier.getShoppingCart();
//        shoppingCart.setUser(user);
//        ShoppingCart shoppingCartFromDb = shoppingCartRepository.save(shoppingCart);
//        return shoppingCartFromDb;
//    }


}
