package com.example.bookstore.repository.book;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import java.math.BigDecimal;
import java.util.List;
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
    private Book book;
    private Category categoryThriller;
    private Category categoryHistorical;
    private Category savedCategoryThriller;
    private Category savedCategoryHistorical;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Test book 1");
        book.setAuthor("test book author");
        book.setDescription("interesting test book");
        book.setIsbn("isbnunique");
        book.setPrice(new BigDecimal(1000));
        book.setCoverImage("images/testBookCover.jpg");
        categoryThriller = new Category();
        categoryThriller.setName("Thriller");
        categoryThriller.setDescription("thriller books in this category");
        savedCategoryThriller = categoryRepository.save(categoryThriller);
        //        categoryHistorical = new Category();
        //        categoryHistorical.setName("Historical");
        //        categoryHistorical.setDescription("Historical cat is for historical books");
        //        savedCategoryHistorical = categoryRepository.save(categoryHistorical);
        //        Set<Category> categorySet =
        //        Set.of(savedCategoryThriller,  savedCategoryHistorical);
        Set<Category> categorySet = Set.of(savedCategoryThriller);
        book.setCategories(categorySet);
        bookRepository.save(book);
    }

    @Test
    @DisplayName("Find ")
    void findAllByCategoryId_ValidCatId_ReturnsListWithOneBook() {
        List<Book> actual = bookRepository.findAllByCategoryId(savedCategoryThriller.getId());

        assertThat(actual.size()).isEqualTo(1);
    }
}
