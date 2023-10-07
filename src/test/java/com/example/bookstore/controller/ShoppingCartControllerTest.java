package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.request.CartItemRequestDto;
import com.example.bookstore.dto.response.CartItemResponseDto;
import com.example.bookstore.dto.response.ShoppingCartResponseDto;
import com.example.bookstore.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Check if get() returns a shoppingCart of the user")
    @Sql(scripts = {
            "classpath:database/shoppingCarts/add-shoppingCart-to-shoppingCart-table.sql",
            "classpath:database/books/add-book-to-books-table.sql",
            "classpath:database/cartItem/add-cartItems-to-cart_items-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingCarts/delete-shoppingCarts-from-shopping_carts-table.sql",
            "classpath:database/cartItem/delete-cartItems-from-cart_items-table.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void get_ValidShoppingCart_Success() throws Exception {
        //given
        User user = new User();
        user.setId(1L);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ShoppingCartResponseDto expected = getShoppingCartResponseDto();
        //when
        MvcResult result = mockMvc.perform(get("/cart")
                        .with(authentication(authentication))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        ShoppingCartResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ShoppingCartResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @NotNull
    private static ShoppingCartResponseDto getShoppingCartResponseDto() {
        ShoppingCartResponseDto expected = new ShoppingCartResponseDto();
        expected.setId(1L);
        expected.setUserId(1L);
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setBookId(1L);
        cartItemResponseDto.setQuantity(5);
        cartItemResponseDto.setBookTitle("testBook");

        Set<CartItemResponseDto> cartItemsResponseDtos = new HashSet<>();
        cartItemsResponseDtos.add(cartItemResponseDto);
        expected.setCartItemsDto(cartItemsResponseDtos);
        return expected;
    }

    @Test
    @DisplayName("Check whether addBook() works")
    @Sql(scripts = {
            "classpath:database/shoppingCarts/add-shoppingCart-to-shoppingCart-table.sql",
            "classpath:database/books/add-book-to-books-table.sql",
            "classpath:database/users/add-user-to-users-table.sql",
            "classpath:database/users_roles/add-user-role-to-user_roles.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/shoppingCarts/delete-shoppingCarts-from-shopping_carts-table.sql",
            "classpath:database/cartItem/delete-cartItems-from-cart_items-table.sql",
            "classpath:database/books/delete-books-from-books-table.sql",
            "classpath:database/users/delete-from-users-table.sql",
            "classpath:database/users_roles/delete-from-users_roles.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addBook_ValidBook_Success() throws Exception {
        //given
        CartItemResponseDto cartItemResponseDto = new CartItemResponseDto();
        cartItemResponseDto.setQuantity(36);
        cartItemResponseDto.setId(1L);
        cartItemResponseDto.setBookId(1L);
        cartItemResponseDto.setBookTitle("testBook");
        Set<CartItemResponseDto> cartItemsDto = new HashSet<>();
        cartItemsDto.add(cartItemResponseDto);
        ShoppingCartResponseDto expected = new ShoppingCartResponseDto();
        expected.setCartItemsDto(cartItemsDto);

        User user = new User();
        user.setId(1L);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, "password", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        CartItemRequestDto requestDto = new CartItemRequestDto();
        requestDto.setBookId(1L);
        requestDto.setQuantity(36);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //when
        MvcResult result = mockMvc.perform(post("/cart")
                        .content(jsonRequest)
                        .with(authentication(authentication))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        ShoppingCartResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ShoppingCartResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }
}
