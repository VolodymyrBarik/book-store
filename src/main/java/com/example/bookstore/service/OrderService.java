package com.example.bookstore.service;

import com.example.bookstore.dto.OrderPlacedConfirmation;
import com.example.bookstore.dto.request.OrderRequestDto;
import com.example.bookstore.dto.request.OrderStatusRequestDto;
import com.example.bookstore.dto.response.OrderResponseDto;
import com.example.bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderPlacedConfirmation create(User user, OrderRequestDto dto);

    List<OrderResponseDto> findAll(User user, Pageable pageable);

    void updateStatus(Long orderId, OrderStatusRequestDto statusRequestDto);
}
