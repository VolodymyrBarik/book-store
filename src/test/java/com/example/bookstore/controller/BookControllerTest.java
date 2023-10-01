package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.request.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
    @Sql(scripts = {
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void create_ValidRequestDto_Success() throws Exception {
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
        expected.setCoverImage(requestDto.getCoverImage());
        expected.setCategoriesId(requestDto.getCategoriesId());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(
                        post("/books")
                                .content(jsonRequest)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @DisplayName("Checks if getAll() returns all the books")
    @WithMockUser(username = "user")
    @Sql(scripts = {
            "classpath:database/books/add-five-books-to-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAll_ValidData_Success() throws Exception {

        BookDto bookDto1 = new BookDto();
        bookDto1.setId(1L);
        bookDto1.setTitle("testBook1");
        bookDto1.setAuthor("testAuthor1");
        bookDto1.setDescription("interesting test book1");
        bookDto1.setIsbn("isbnunique1");
        bookDto1.setPrice(new BigDecimal(1000));
        bookDto1.setCoverImage("coverImage1.jpg");

        List<BookDto> expected = new ArrayList<>();
        expected.add(bookDto1);

        BookDto bookDto2 = new BookDto();
        bookDto2.setId(2L);
        bookDto2.setTitle("testBook2");
        bookDto2.setAuthor("testAuthor2");
        bookDto2.setDescription("interesting test book2");
        bookDto2.setIsbn("isbnunique2");
        bookDto2.setPrice(new BigDecimal(1000));
        bookDto2.setCoverImage("coverImage2.jpg");
        expected.add(bookDto2);

        BookDto bookDto3 = new BookDto();
        bookDto3.setId(3L);
        bookDto3.setTitle("testBook3");
        bookDto3.setAuthor("testAuthor3");
        bookDto3.setDescription("interesting test book3");
        bookDto3.setIsbn("isbnunique3");
        bookDto3.setPrice(new BigDecimal(1000));
        bookDto3.setCoverImage("coverImage3.jpg");
        expected.add(bookDto3);

        BookDto bookDto4 = new BookDto();
        bookDto4.setId(4L);
        bookDto4.setTitle("testBook4");
        bookDto4.setAuthor("testAuthor4");
        bookDto4.setDescription("interesting test book4");
        bookDto4.setIsbn("isbnunique4");
        bookDto4.setPrice(new BigDecimal(1000));
        bookDto4.setCoverImage("coverImage4.jpg");
        expected.add(bookDto4);

        BookDto bookDto5 = new BookDto();
        bookDto5.setId(5L);
        bookDto5.setTitle("testBook5");
        bookDto5.setAuthor("testAuthor5");
        bookDto5.setDescription("interesting test book5");
        bookDto5.setIsbn("isbnunique5");
        bookDto5.setPrice(new BigDecimal(1000));
        bookDto5.setCoverImage("coverImage5.jpg");
        expected.add(bookDto5);

        MvcResult result = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(result.getResponse()
                .getContentAsByteArray(), BookDto[].class);

        Assertions.assertEquals(expected.size(),
                Arrays.stream(actual).toList().size());
        Assertions.assertEquals(expected.get(0).getCoverImage(),
                Arrays.stream(actual).toList().get(0).getCoverImage());
        Assertions.assertEquals(expected.get(1).getTitle(),
                Arrays.stream(actual).toList().get(1).getTitle());
        Assertions.assertEquals(expected.get(2).getAuthor(),
                Arrays.stream(actual).toList().get(2).getAuthor());
        Assertions.assertEquals(expected.get(3).getIsbn(),
                Arrays.stream(actual).toList().get(3).getIsbn());
        Assertions.assertEquals(expected.get(4).getDescription(),
                Arrays.stream(actual).toList().get(4).getDescription());
    }

    @Test
    @Sql(scripts = {
            "classpath:database/books/add-book-to-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user")
    @DisplayName("Checks if getBookById() works")
    void getBookById_ValidBookId_Success() throws Exception {
        BookDto expected = new BookDto();
        expected.setId(1L);
        expected.setAuthor("testAuthor");
        expected.setTitle("testBook");
        expected.setDescription("interesting test book");
        expected.setIsbn("isbnunique");
        expected.setPrice(new BigDecimal(1000));
        expected.setCoverImage("coverImage.jpg");

        MvcResult result = mockMvc.perform(get("/books/{id}", expected.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), BookDto.class);

        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {
            "classpath:database/books/add-book-to-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Checks if getBookById() works")
    void delete_ValidBookId_Success() throws Exception {
        Long idToBeDeleted = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/books/{id}", idToBeDeleted)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        MvcResult result = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actual = objectMapper.readValue(result.getResponse()
                .getContentAsByteArray(), BookDto[].class);

        Assertions.assertEquals(actual.length, 0);
    }
}
