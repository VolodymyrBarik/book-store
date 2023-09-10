package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    OrderItem toRequestDto(CartItem cartItem);
}
