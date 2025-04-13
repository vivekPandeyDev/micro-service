package org.loop.troop.service;

import org.loop.troop.dto.BookDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {
    BookDto createBook(BookDto dto);
    BookDto updateBook(UUID id, BookDto dto);
    void deleteBook(UUID id);
    Page<BookDto> getBooks(int page, int size);
    BookDto getBook(UUID id);
}
