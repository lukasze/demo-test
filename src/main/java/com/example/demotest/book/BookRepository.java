package com.example.demotest.book;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {

    final private List<Book> bookList = new ArrayList<>();
    {
        bookList.add(Book.builder().author("Miłosz Brzeziński").title("Wszyscy moi ja").build());
        bookList.add(Book.builder().author("Yuval Noah Harari").title("Sapiens. Od zwierząt do bogów").build());
        bookList.add(Book.builder().author("Andrzej Leder").title("Prześniona rewolucja").build());
        bookList.add(Book.builder().author("MICHAEL POLLAN").title("How to Change Your Mind").build());
    }

    public List<Book> getAll() {
        return bookList;
    }

    public void save(Book book) {
        bookList.add(book);
    }
}
