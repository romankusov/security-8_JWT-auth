package org.example.jwtauth.service;

import lombok.RequiredArgsConstructor;
import org.example.jwtauth.model.Book;
import org.example.jwtauth.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book postNewBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
