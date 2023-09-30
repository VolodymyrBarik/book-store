package com.example.bookstore.repository.book;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private Category savedCategoryThriller;

    @BeforeEach
    void setUp() {
        Book book = new Book();
        book.setTitle("Test book 1");
        book.setAuthor("test book author");
        book.setDescription("interesting test book");
        book.setIsbn("isbnunique");
        book.setPrice(new BigDecimal(1000));
        book.setCoverImage("images/testBookCover.jpg");
        Category categoryThriller = new Category();
        categoryThriller.setName("Thriller");
        categoryThriller.setDescription("thriller books in this category");
        savedCategoryThriller = categoryRepository.save(categoryThriller);
        Category categoryHistorical = new Category();
        categoryHistorical.setName("Historical");
        categoryHistorical.setDescription("Historical cat is for historical books");
        Category savedCategoryHistorical = categoryRepository.save(categoryHistorical);
        Set<Category> categorySet = Set.of(savedCategoryThriller, savedCategoryHistorical);
        book.setCategories(categorySet);
        bookRepository.save(book);
    }

    @Test
    @DisplayName("Checks findAllByCategoryId with valid data")
    void findAllByCategoryId_ValidCatId_ReturnsListWithOneBook() {
        int expected = 1;
        List<Book> actual = bookRepository.findAllByCategoryId(savedCategoryThriller.getId());
        assertThat(actual.size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Checks findAllWithCategories with valid data")
    void findAllWithCategories_Valid_ReturnsAllTheBooks() {
        int expected = 2;
        List<Book> allBooks = bookRepository.findAll();
        Book actual = allBooks.get(0);
        assertThat(actual.getCategories().size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Checks findByIdWithCategories with valid data")
    void findByIdWithCategories_ValidId_ReturnsBook() {
        long validId = 1;
        int expected = 2;
        Book actual = bookRepository.findByIdWithCategories(validId).get();
        assertThat(actual.getCategories().size()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Checks findByIdWithCategories with invalid data, exception should be returned")
    void findByIdWithCategories_InvalidId_ReturnsException() {
        long invalidId = 999;
        RuntimeException exception = assertThrows(
                NoSuchElementException.class, () -> bookRepository
                        .findByIdWithCategories(invalidId).get());
        assertThat(exception.getMessage()).isEqualTo("No value present");
    }
}
