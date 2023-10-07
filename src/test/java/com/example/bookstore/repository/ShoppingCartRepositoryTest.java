package com.example.bookstore.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.bookstore.model.ShoppingCart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("Checks if findShoppingCartByUser_Id returns shoppingCart required")
    @Sql(scripts = {
            "classpath:database/shoppingCarts/add-shoppingCart-to-shoppingCart-table.sql",
            "classpath:database/cartItem/add-cartItems-to-cart_items-table.sql",
            "classpath:database/books/add-book-to-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingCarts/delete-shoppingCarts-from-shopping_carts-table.sql",
            "classpath:database/cartItem/delete-cartItems-from-cart_items-table.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findShoppingCartByUser_Id_ValidId_Success() {
        ShoppingCart actual = shoppingCartRepository.findShoppingCartByUser_Id(1L).get();
        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getUser().getId()).isEqualTo(1L);
        assertThat(actual.getCartItems().size()).isEqualTo(1);
    }
}
