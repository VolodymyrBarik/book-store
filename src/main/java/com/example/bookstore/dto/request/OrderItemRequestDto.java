package com.example.bookstore.dto.request;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Order;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {
    private Order order;
    private Book book;
    private int quantity;
    private BigDecimal price;
}
