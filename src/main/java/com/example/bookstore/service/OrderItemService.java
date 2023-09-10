package com.example.bookstore.service;

import com.example.bookstore.model.OrderItem;
import java.util.Set;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    Set<OrderItem> getOrderItemBy();
}
