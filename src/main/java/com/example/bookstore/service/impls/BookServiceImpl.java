package com.example.bookstore.service.impls;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.BookSearchParameters;
import com.example.bookstore.dto.request.CreateBookRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.repository.book.BookRepository;
import com.example.bookstore.repository.book.BookSpecificationBuilder;
import com.example.bookstore.service.BookService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = mapper.toModel(requestDto);
        Book bookFromDb = bookRepository.save(book);
        Set<Category> categories = requestDto.getCategoriesId().stream()
                .map(i -> categoryRepository.findById(i).orElse(new Category()))
                .collect(Collectors.toSet());
        bookFromDb.setCategories(categories);
        return mapper.toDto(bookFromDb);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAllWithCategories(pageable).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findByIdWithCategories(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id));
        return mapper.toDto(book);
    }

    @Override
    public void update(Long id, CreateBookRequestDto dto) {
        Book bookFromDb = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id " + id));
        bookFromDb.setAuthor(dto.getAuthor());
        bookFromDb.setTitle(dto.getTitle());
        bookFromDb.setDescription(dto.getDescription());
        bookFromDb.setIsbn(dto.getIsbn());
        bookFromDb.setPrice(dto.getPrice());
        bookFromDb.setCoverImage(dto.getCoverImage());
        Set<Category> categories = dto.getCategoriesId().stream()
                .map(i -> categoryRepository.findById(i).orElse(new Category()))
                .collect(Collectors.toSet());
        bookFromDb.setCategories(categories);
        bookRepository.save(bookFromDb);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);

    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters, Pageable pageable) {
        Specification<Book> bookSpecification
                = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification, pageable).stream()
                .map(b -> bookRepository.findByIdWithCategories(b.getId()).orElse(new Book()))
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getAllBooksByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(mapper::toDto)
                .toList();
    }
}
