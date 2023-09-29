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
    @DisplayName("Find all books by category id")
    void findAllByCategoryId_ValidCatId_ReturnsListWithOneBook() {
        List<Book> actual = bookRepository.findAllByCategoryId(savedCategoryThriller.getId());
        assertThat(actual.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Find all the books")
    void findAllWithCategories_Valid_ReturnsAllTheBooks() {
        List<Book> allBooks = bookRepository.findAll();
        Book book1 = allBooks.get(0);
        assertThat(book1.getCategories().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Finds a book with categories by id")
    void findByIdWithCategories_ValidId_ReturnsBook() {
        long validId = 1;
        Book book1 = bookRepository.findByIdWithCategories(validId).get();
        assertThat(book1.getCategories().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Finds a book with categories by id")
    void findByIdWithCategories_InvalidId_ReturnsException() {
        long invalidId = 999;

        RuntimeException exception = assertThrows(
                NoSuchElementException.class, () -> bookRepository.findByIdWithCategories(invalidId).get());

        assertThat(exception.getMessage()).isEqualTo("No value present");
    }
}
