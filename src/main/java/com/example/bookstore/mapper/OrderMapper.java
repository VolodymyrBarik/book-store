package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.response.OrderItemResponseDto;
import com.example.bookstore.dto.response.OrderResponseDto;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    @Mapping(source = "order.user.id", target = "userId")
    @Mapping(source = "order.status", target = "status")
    OrderResponseDto toDto(Order order);

    default Set<OrderItemResponseDto> mapOrderItemsSetToOrderItemsResponseDtoSet(
            Set<OrderItem> orderItems) {
        return orderItems.stream()
                .map(this::mapOrderItemToResponseDto)
                .collect(Collectors.toSet());
    }

    default OrderItemResponseDto mapOrderItemToResponseDto(OrderItem orderItem) {
        OrderItemResponseDto responseDto = new OrderItemResponseDto();
        responseDto.setId(orderItem.getId());
        responseDto.setBookId(orderItem.getBook().getId());
        responseDto.setQuantity(orderItem.getQuantity());
        return responseDto;
    }
}
