package com.example.demotest.book;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/books")
    void getAll() {

    }
}
