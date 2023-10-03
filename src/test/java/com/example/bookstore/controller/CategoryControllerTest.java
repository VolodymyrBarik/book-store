package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.request.CategoryRequestDto;
import com.example.bookstore.dto.response.CategoryResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
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
    @DisplayName("Creates a new category")
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_ValidRequestDto_Success() throws Exception {
        //given
        CategoryRequestDto requestDto = new CategoryRequestDto();
        requestDto.setName("Test Category");
        requestDto.setDescription("Test Category made especially for tests");

        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setId(1L);
        expected.setName(requestDto.getName());
        expected.setDescription(requestDto.getDescription());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        //when
        MvcResult result = mockMvc.perform(
                        post("/categories")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        //then
        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Checks getAll() returns all the categories")
    @WithMockUser(username = "user")
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_ValidTwoCategories_Success() throws Exception {
        //given
        CategoryResponseDto dto1 = new CategoryResponseDto();
        dto1.setId(1L);
        dto1.setName("Thriller");
        dto1.setDescription("thriller books in this category");
        List<CategoryResponseDto> expected = new ArrayList<>();
        expected.add(dto1);

        CategoryResponseDto dto2 = new CategoryResponseDto();
        dto2.setId(2L);
        dto2.setName("Historical");
        dto2.setDescription("historical books in this category");
        expected.add(dto2);
        //when
        MvcResult result = mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        CategoryResponseDto[] actual = objectMapper.readValue(result.getResponse()
                .getContentAsByteArray(), CategoryResponseDto[].class);

        Assertions.assertEquals(expected.size(), Arrays.stream(actual).toList().size());
        Assertions.assertEquals(expected.get(0).getName(), actual[0].getName());
        Assertions.assertEquals(expected.get(1).getDescription(), actual[1].getDescription());

    }

    @Test
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user")
    @DisplayName("Checks if getBookById() returns category expected")
    void getById_ValidCategoryId_Success() throws Exception {
        //given
        CategoryResponseDto expected = new CategoryResponseDto();
        expected.setId(2L);
        expected.setName("Historical");
        expected.setDescription("historical books in this category");
        //when
        MvcResult result = mockMvc.perform(get("/categories/{id}", expected.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        CategoryResponseDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), CategoryResponseDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Deletes a single book from database")
    void delete_ValidCategoryId_Success() throws Exception {
        //given
        Long idToBeDeleted = 2L;
        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/categories/{id}", idToBeDeleted)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        MvcResult result = mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        CategoryResponseDto[] actual = objectMapper.readValue(result.getResponse()
                .getContentAsByteArray(), CategoryResponseDto[].class);

        Assertions.assertEquals(actual.length, 1);
    }
}
