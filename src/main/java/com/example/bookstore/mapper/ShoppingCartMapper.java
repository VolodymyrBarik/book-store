package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.CartItemResponseDto;
import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    @Mapping(source = "shoppingCart.user.id", target = "userId")
    @Mapping(source = "cartItems", target = "cartItemsDto")
    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    default Set<CartItemResponseDto> mapCartItemsSetToCartItemsResponseDtoSet(
            Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::mapCartItemToResponseDto)
                .collect(Collectors.toSet());
    }

    default CartItemResponseDto mapCartItemToResponseDto(CartItem cartItem) {
        CartItemResponseDto responseDto = new CartItemResponseDto();
        responseDto.setId(cartItem.getId());
        responseDto.setBookId(cartItem.getBook().getId());
        responseDto.setBookTitle(cartItem.getBook().getTitle());
        responseDto.setQuantity(cartItem.getQuantity());
        return responseDto;
    }
}
