package com.example.bookstore.controller;

import com.example.bookstore.dto.request.OrderRequestDto;
import com.example.bookstore.dto.request.OrderStatusRequestDto;
import com.example.bookstore.dto.response.OrderItemResponseDto;
import com.example.bookstore.dto.response.OrderResponseDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.OrderItemService;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Order management", description = "Endpoints to manage orders")
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order")
    OrderResponseDto create(
            @RequestBody OrderRequestDto requestDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.create(user, requestDto);
    }

    @GetMapping
    @Operation(summary = "Returns all the orders belongs to user",
            description = "Returns list of orders belongs to user")
    List<OrderResponseDto> getAll(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAll(user, pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "Returns all the items belongs to order",
            description = "Returns list of orderItems that certain order contains")
    List<OrderItemResponseDto> getAllItemsFromOrder(
            @PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getOrderItemByOrderId(orderId, user);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    @Operation(summary = "Returns certain from the certain order",
            description = "Returns certain from the certain order that belongs to user")
    OrderItemResponseDto getItemFromOrder(
            @PathVariable Long orderId, @PathVariable Long orderItemId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderItemService.getItemFromOrder(orderId, user, orderItemId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update order status",
            description = "Updates order status by admin")
    void updateStatus(@PathVariable Long orderId,
                      @RequestBody OrderStatusRequestDto requestStatusDto) {
        orderService.updateStatus(orderId, requestStatusDto);
    }
}