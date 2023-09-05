package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.ShoppingCartResponseDto;
import com.example.bookstore.model.ShoppingCart;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {

    ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);
}
