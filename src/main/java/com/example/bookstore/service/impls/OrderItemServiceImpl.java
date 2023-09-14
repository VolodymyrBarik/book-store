package com.example.bookstore.service.impls;

import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItemResponseDto> getOrderItemByOrderId(Long orderId, User user) {
        List<Long> userOrdersIds = orderRepository.findAllByUserId(
                        user.getId(), Pageable.unpaged()).stream()
                .map(Order::getId)
                .toList();
        if (userOrdersIds.contains(orderId)) {
            return orderItemRepository.findAllByOrderId(orderId).stream()
                    .map(orderItemMapper::toResponseDto)
                    .toList();
        } else {
            throw new EntityNotFoundException(
                    user.getFirstName() + " " + user.getLastName()
                            + " doesn't have order with number # " + orderId);
        }
    }

    @Override
    public OrderItemResponseDto getItemFromOrder(Long orderId, User user, Long orderItemId) {
        List<Long> orderItemsIds = getOrderItemByOrderId(orderId, user).stream()
                .map(OrderItemResponseDto::getId)
                .toList();
        if (orderItemsIds.contains(orderItemId)) {
            OrderItem orderItem = orderItemRepository.findById(orderItemId).get();
            return orderItemMapper.toResponseDto(orderItem);
        }
        throw new EntityNotFoundException("No such item "
                + orderItemId + " in this order " + orderId);
    }
}

