package com.example.demotest.book;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    final private List<Book> bookList = List.of(
            Book.builder().author("Miłosz Brzeziński").title("Wszyscy moi ja").build(),
            Book.builder().author("Yuval Noah Harari").title("Sapiens. Od zwierząt do bogów").build(),
            Book.builder().author("Andrzej Leder").title("Prześniona rewolucja").build(),
            Book.builder().author("MICHAEL POLLAN").title("How to Change Your Mind").build()
    );

    public List<Book> getAll() {
        return bookList;
    }
}
