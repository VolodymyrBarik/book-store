package com.example.bookstore.service.impls;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.request.CartItemRequestDto;
import com.example.bookstore.dto.response.ShoppingCartResponseDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.book.BookRepository;
import com.example.bookstore.service.ShoppingCartService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {
    @InjectMocks
    private CartItemServiceImpl cartItemService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private ShoppingCartService shoppingCartService;
    private CartItemRequestDto cartItemRequestDto;
    private User user;
    private ShoppingCartResponseDto shoppingCartResponseDto;
    private CartItem expected;

    @BeforeEach
    void setUp() {
        cartItemRequestDto = new CartItemRequestDto();
        cartItemRequestDto.setBookId(1L);
        cartItemRequestDto.setQuantity(5);

        expected = new CartItem();
        expected.setId(1L);
        user = new User();
        shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setId(4526L);
    }

    @Test
    void create() {
        //given
        when(shoppingCartService.get(user)).thenReturn(shoppingCartResponseDto);
        when(cartItemRepository.findByShoppingCartIdAndBook_id(
                shoppingCartResponseDto.getId(), cartItemRequestDto.getBookId()))
                .thenReturn(Optional.ofNullable(expected));
        when(cartItemRepository.save(expected)).thenReturn(expected);
        when(cartItemRepository.save(expected)).thenReturn(expected);
        //when
        CartItem actual = cartItemService.create(cartItemRequestDto, user);
        //then
        assertThat(actual).isEqualTo(expected);
        verify(cartItemRepository, times(2)).findByShoppingCartIdAndBook_id(
                shoppingCartResponseDto.getId(), cartItemRequestDto.getBookId());
        verifyNoMoreInteractions(shoppingCartService, cartItemRepository, bookRepository);
    }
}
