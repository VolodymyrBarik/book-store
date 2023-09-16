package com.example.bookstore.service.impls;

import com.example.bookstore.dto.request.OrderRequestDto;
import com.example.bookstore.dto.request.OrderStatusRequestDto;
import com.example.bookstore.dto.response.OrderItemResponseDto;
import com.example.bookstore.dto.response.OrderResponseDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.OrderItemMapper;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDto create(User user, OrderRequestDto dto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser_Id(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "You're shopping cart is empty, "
                                + "you have to place good's into the shopping cart"));
        Order orderFromDb = setUpOrder(user, dto, shoppingCart);
        createOrderItems(shoppingCart, orderFromDb);
        return getOrderConfirmation(orderFromDb, user);
    }

    @Override
    public List<OrderResponseDto> findAll(User user, Pageable pageable) {
        List<Order> allOrderByUserId = orderRepository.findAllByUserId(user.getId(), pageable);
        return allOrderByUserId.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(Long orderId, OrderStatusRequestDto statusRequestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Can't find order number # " + orderId));
        try {
            order.setStatus(Order.Status.valueOf(statusRequestDto.getStatus()));
            orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + statusRequestDto.getStatus());
        }
    }

    private OrderItem parseCartItemIntoOrderItem(Order order, CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        BigDecimal price = new BigDecimal(String.valueOf(cartItem.getBook().getPrice()));
        price = price.multiply(new BigDecimal(cartItem.getQuantity()));
        orderItem.setPrice(price);
        return orderItem;
    }

    private BigDecimal getTotalPrice(Set<CartItem> items) {
        return items.stream()
                .mapToDouble(cartItem -> cartItem.getBook().getPrice().doubleValue()
                        * cartItem.getQuantity())
                .mapToObj(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private OrderResponseDto getOrderConfirmation(Order orderFromDb, User user) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(orderFromDb.getId());
        responseDto.setOrderDate(orderFromDb.getOrderDate());
        Set<OrderItemResponseDto> orderItemsResponseDtoSet = orderFromDb.getOrderItems().stream()
                .map(orderItemMapper::toResponseDto)
                .collect(Collectors.toSet());
        responseDto.setOrderItems(orderItemsResponseDtoSet);
        responseDto.setUserId(orderFromDb.getUser().getId());
        responseDto.setStatus(orderFromDb.getStatus().toString());
        responseDto.setTotal(orderFromDb.getTotal());
        return responseDto;
    }

    private void createOrderItems(ShoppingCart shoppingCart, Order orderFromDb) {
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = parseCartItemIntoOrderItem(orderFromDb, cartItem);
            orderItemRepository.save(orderItem);
            deleteCartItemFromShoppingCart(cartItem);
            cartItemRepository.save(cartItem);
        }
    }

    private void deleteCartItemFromShoppingCart(CartItem cartItem) {
        cartItem.setDeleted(true);
    }

    private Order setUpOrder(User user, OrderRequestDto dto, ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setStatus(Order.Status.NEW);
        order.setUser(user);
        order.setShippingAddress(dto.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());
        BigDecimal totalPrice = getTotalPrice(shoppingCart.getCartItems());
        order.setTotal(totalPrice);
        return saveOrderToDb(order);
    }

    private Order saveOrderToDb(Order order) {
        return orderRepository.save(order);
    }
}