package com.example.demotest.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    final private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/books")
    List<Book> getAll() {
        return bookRepository.getAll();
    }

    @PostMapping("/books")
    void create(@RequestBody Book book) {
        bookRepository.save(book);
    }
}
