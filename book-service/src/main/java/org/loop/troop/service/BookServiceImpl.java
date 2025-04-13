package org.loop.troop.service;


import lombok.RequiredArgsConstructor;
import org.loop.troop.dto.BookDto;
import org.loop.troop.entity.Book;
import org.loop.troop.exception.ServiceException;
import org.loop.troop.repo.BookRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookDto createBook(BookDto dto) {
        Book book = modelMapper.map(dto, Book.class);
        book.setLastUpdated(LocalDateTime.now());
        Book saved = bookRepository.save(book);
        return convertToDto(saved);
    }

    public BookDto updateBook(UUID id, BookDto dto) {
        Book existing = bookRepository.findById(id).orElseThrow(() -> new ServiceException("Book not found", HttpStatus.NOT_FOUND));
        modelMapper.map(dto, existing);
        existing.setLastUpdated(LocalDateTime.now());
        return convertToDto(bookRepository.save(existing));
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }

    public Page<BookDto> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("lastUpdated").descending());
        return bookRepository.findAll(pageable).map(this::convertToDto);
    }

    public BookDto getBook(UUID id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new ServiceException("Book not found", HttpStatus.NOT_FOUND));
        return convertToDto(book);
    }

    private BookDto convertToDto(Book book) {
        BookDto dto = modelMapper.map(book, BookDto.class);
        dto.setLastUpdated(humanReadableTime(book.getLastUpdated()));
        dto.setAuthorName(getAuthorName(book.getAuthorId()));
        return dto;
    }

    private String humanReadableTime(LocalDateTime updated) {
        Duration duration = Duration.between(updated, LocalDateTime.now());
        long days = duration.toDays();
        long hours = duration.toHours();

        if (days > 0) return "about " + days + " days ago";
        if (hours > 0) return "about " + hours + " hours ago";
        return "just now";
    }

    private String getAuthorName(UUID authorId) {
        // Simulate user-service call (Replace with actual RestTemplate/WebClient logic)
        if (authorId == null) return null;
        return "Author Name from user-service"; // Replace with real call
    }
}
