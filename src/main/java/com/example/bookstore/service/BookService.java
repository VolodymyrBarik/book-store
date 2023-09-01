package com.example.bookstore.service;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.BookSearchParameters;
import com.example.bookstore.dto.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void update(Long id, CreateBookRequestDto dto);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    List<BookDto> getAllBooksByCategoryId(Long categoryId);
}
