package com.example.bookstore.repository;

import com.example.bookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    //
    //@Query("SELECT sc FROM ShoppingCart sc JOIN FETCH CartItem. WHERE sc.user=:id")
    @Query("SELECT sc FROM ShoppingCart sc JOIN FETCH CartItem ci"
            + " WHERE ci.shoppingCart=: id AND ci.isDeleted=false")
    Optional<ShoppingCart> findShoppingCartBuId(Long id);

    @Query("SELECT sc FROM ShoppingCart sc LEFT JOIN FETCH sc.user u WHERE u.id=:id")
    //@Query("SELECT sc FROM ShoppingCart sc LEFT JOIN FETCH sc.user u WHERE u.id=:id")
    Optional<ShoppingCart> findShoppingCartByUser_Id(Long id);
}
