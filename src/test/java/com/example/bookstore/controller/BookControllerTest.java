package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.request.CreateBookRequestDto;
import java.math.BigDecimal;
import java.util.Set;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

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
    @DisplayName("Create a new book")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void create_ValidRequestDto_Success() throws Exception {
        //Given


        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Test book");
        requestDto.setAuthor("Test author");
        requestDto.setIsbn("uniqueTestIsbn");
        requestDto.setPrice(new BigDecimal(500));
        requestDto.setDescription("blablabla");
        requestDto.setCoverImage("dtoCoverImage.jpeg");
        requestDto.setCategoriesId(Set.of(1L, 2L));

        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setTitle(requestDto.getTitle());
        expected.setAuthor(requestDto.getAuthor());
        expected.setIsbn(requestDto.getIsbn());
        expected.setPrice(requestDto.getPrice());
        expected.setDescription(requestDto.getDescription());
        expected.setCategoriesId(requestDto.getCategoriesId());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        System.out.println(jsonRequest);

        //When
        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        //Then
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expected.getAuthor(), actual.getAuthor());

    }

    @Test
    void getAll() {
    }

    @Test
    void getBookById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void search() {
    }
}