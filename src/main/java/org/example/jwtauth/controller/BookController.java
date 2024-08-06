package org.example.jwtauth.controller;

import lombok.RequiredArgsConstructor;
import org.example.jwtauth.model.Book;
import org.example.jwtauth.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping("/admin/postNewBook")
    public ResponseEntity<Book> postNewBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.postNewBook(book));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
