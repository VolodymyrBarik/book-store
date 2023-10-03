package com.example.bookstore.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.bookstore.model.Book;
import com.example.bookstore.repository.book.BookRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Checks findAllByCategoryId with valid data")
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql",
            "classpath:database/books/add-book-to-books-table.sql",
            "classpath:database/books_categories/add-categories-to-book.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql",
            "classpath:database/books_categories/delete-all-from-books_categories.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllByCategoryId_ValidCatId_ReturnsListWithOneBook() {
        int expected = 1;
        List<Book> actual = bookRepository.findAllByCategoryId(1L);
        assertThat(actual.size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Checks findAllWithCategories with valid data")
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql",
            "classpath:database/books/add-book-to-books-table.sql",
            "classpath:database/books_categories/add-categories-to-book.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql",
            "classpath:database/books_categories/delete-all-from-books_categories.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAllWithCategories_Valid_ReturnsAllTheBooks() {
        int expected = 2;
        List<Book> allBooks = bookRepository.findAll();
        Book actual = allBooks.get(0);
        assertThat(actual.getCategories().size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Checks findByIdWithCategories with valid data")
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql",
            "classpath:database/books/add-book-to-books-table.sql",
            "classpath:database/books_categories/add-categories-to-book.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql",
            "classpath:database/books_categories/delete-all-from-books_categories.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByIdWithCategories_ValidId_ReturnsBook() {
        long validId = 1;
        int expected = 2;
        Book actual = bookRepository.findByIdWithCategories(validId).get();
        assertThat(actual.getCategories().size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Checks findByIdWithCategories with invalid data, exception should be returned")
    @Sql(scripts = {
            "classpath:database/categories/add-categories-to-categories-table.sql",
            "classpath:database/books/add-book-to-books-table.sql",
            "classpath:database/books_categories/add-categories-to-book.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/categories/delete-categories-from-categories-table.sql",
            "classpath:database/books_categories/delete-all-from-books_categories.sql",
            "classpath:database/books/delete-books-from-books-table.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findByIdWithCategories_InvalidId_ReturnsException() {
        long invalidId = 999;
        RuntimeException exception = assertThrows(
                NoSuchElementException.class, () -> bookRepository
                        .findByIdWithCategories(invalidId).get());
        assertThat(exception.getMessage()).isEqualTo("No value present");
    }
}
