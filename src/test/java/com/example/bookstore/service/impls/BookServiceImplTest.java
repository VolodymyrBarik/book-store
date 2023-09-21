package com.example.bookstore.service.impls;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.request.CreateBookRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.repository.book.BookRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookMapper mapper;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    private CreateBookRequestDto requestDto;
    private Book book;
    private BookDto bookDto;
    private Long bookId;

    @BeforeEach
    void setUp() {
        requestDto = new CreateBookRequestDto();
        Set<Long> categoriesIdSet = new HashSet<>();
        Long categoryId = 1L;
        categoriesIdSet.add(categoryId);
        requestDto.setCategoriesId(categoriesIdSet);

        book = new Book();
        Set<Category> categorySet = new HashSet<>();
        Category category = new Category();
        category.setId(categoryId);
        categorySet.add(category);
        book.setCategories(categorySet);
        book.setAuthor("Jorge Bob");
        book.setIsbn("36475849sdg");
        book.setPrice(new BigDecimal(1000));
        book.setTitle("Good book");
        book.setDescription("Good book's description");
        book.setCoverImage("src/image.jpg");

        bookDto = new BookDto();
        bookId = 1L;
        bookDto.setId(bookId);
        bookDto.setCategoriesId(categoriesIdSet);
        bookDto.setAuthor("Jorge Bob");
        bookDto.setIsbn("36475849sdg");
        bookDto.setPrice(new BigDecimal(1000));
        bookDto.setTitle("Good book");
        bookDto.setDescription("Good book's description");
        bookDto.setCoverImage("src/image.jpg");
    }

    @Test
    @DisplayName("Verify save method works")
    void save_ValidCreateBookRequestDto_ReturnsBookDto() {

        when(mapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(mapper.toDto(book)).thenReturn(bookDto);
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(new Category()));

        BookDto savedBookDto = bookService.save(requestDto);

        assertThat(savedBookDto).isEqualTo(bookDto);
        verify(bookRepository, times(1)).save(book);
        verifyNoMoreInteractions(mapper, bookRepository, categoryRepository);
    }

    @Test
    @DisplayName("Verify findById method works")
    void findById_ValidId_ReturnsDto() {

        when(bookRepository.findByIdWithCategories(bookId)).thenReturn(Optional.of(book));
        when(mapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(bookId);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(bookDto);
        verify(bookRepository, times(1)).findByIdWithCategories(bookId);
        verifyNoMoreInteractions(bookRepository, mapper);
    }

    @Test
    @DisplayName("Verify findById method works, returns exception")
    void findById_ValidId_ReturnsException() {
        Long notExistingBookId = 123L;

        when(bookRepository.findByIdWithCategories(notExistingBookId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                EntityNotFoundException.class, () -> bookService.findById(notExistingBookId));

        assertThat(exception.getMessage()).isEqualTo("Can't find book by id " + notExistingBookId);
        verify(bookRepository, times(1)).findByIdWithCategories(notExistingBookId);
        verifyNoMoreInteractions(mapper);
    }
}