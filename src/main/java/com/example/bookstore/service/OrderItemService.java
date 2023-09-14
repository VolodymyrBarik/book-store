package com.example.bookstore.service;

import com.example.bookstore.model.User;
import java.util.List;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    List<OrderItemResponseDto> getOrderItemByOrderId(Long orderId, User user);

    OrderItemResponseDto getItemFromOrder(Long orderId, User user, Long orderItemId);
}
