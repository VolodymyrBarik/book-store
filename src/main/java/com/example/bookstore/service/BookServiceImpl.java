package com.example.bookstore.service;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.repository.BookRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = mapper.toModel(requestDto);
        Book bookFromDB = bookRepository.save(book);
        return mapper.toDto(bookFromDB);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
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
        bookRepository.save(bookFromDb);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
