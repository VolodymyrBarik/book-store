package com.example.bookstore.service.impls;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.response.ShoppingCartResponseDto;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;
    @Mock
    private ShoppingCartMapper shoppingCartMapper;
    @Mock
    private ShoppingCartRepository shoppingCartRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    private User user;
    private ShoppingCart shoppingCart;
    private ShoppingCartResponseDto expected;
    private CartItem cartItem;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        expected = new ShoppingCartResponseDto();
        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        cartItem = new CartItem();
        cartItem.setId(123L);
    }

    @Test
    @DisplayName("Verify method get() returns empty shoppingCart")
    void get_EmptyShoppingCart_Success() {
        //given
        when(shoppingCartRepository.findShoppingCartByUser_Id(user.getId()))
                .thenReturn(Optional.ofNullable(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(expected);
        //when
        ShoppingCartResponseDto actual = shoppingCartService.get(user);
        //then
        assertThat(actual).isEqualTo(expected);
        verify(shoppingCartRepository, times(1)).findShoppingCartByUser_Id(user.getId());
        verifyNoMoreInteractions(shoppingCartRepository, shoppingCartMapper);
    }

    @Test
    @DisplayName("Verify method delete() works")
    void deleteCartItemFromShoppingCart() {
        //given
        when(shoppingCartRepository.findShoppingCartByUser_Id(user.getId()))
                .thenReturn(Optional.ofNullable(shoppingCart));
        when(cartItemRepository.findByIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId()))
                .thenReturn(Optional.ofNullable(cartItem));
        //when
        shoppingCartService.deleteCartItemFromShoppingCart(user, cartItem.getId());
        //then
        verify(cartItemRepository, times(1))
                .findByIdAndShoppingCartId(cartItem.getId(), shoppingCart.getId());
        verify(cartItemRepository, times(1)).delete(cartItem);
        verifyNoMoreInteractions(shoppingCartRepository, cartItemRepository);
    }
}
