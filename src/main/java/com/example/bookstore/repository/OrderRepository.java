package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems"
            + " LEFT JOIN FETCH o.user WHERE o.id =:id")
    Optional<Order> findByIdWithOrderItems(Long id);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems "
            + "LEFT JOIN FETCH o.user WHERE o.user.id =:userId")
    List<Order> findAllByUserId(Long userId, Pageable pageable);
}
