package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderPlacedConfirmation;
import com.example.bookstore.dto.request.OrderRequestDto;
import com.example.bookstore.dto.response.OrderItemResponseDto;
import com.example.bookstore.dto.response.OrderResponseDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.OrderItemService;
import com.example.bookstore.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    OrderPlacedConfirmation create(
            @RequestBody OrderRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.create(user, requestDto);
    }

    @GetMapping
    List<OrderResponseDto> getAll(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAll(user, pageable);
    }

    @GetMapping("/{orderId}/items")
    List<OrderItemResponseDto> getAllItemsFromOrder(
            @PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getOrderItemByOrderId(orderId, user);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    OrderItemResponseDto getItemFromOrder(
            @PathVariable Long orderId, @PathVariable Long orderItemId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getItemFromOrder(orderId, user, orderItemId);
    }

}
