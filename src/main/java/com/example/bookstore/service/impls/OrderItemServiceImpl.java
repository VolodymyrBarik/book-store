package com.example.bookstore.service.impls;

import com.example.bookstore.model.OrderItem;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.service.OrderItemService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public Set<OrderItem> getOrderItemBy() {
        return null;
    }
}
