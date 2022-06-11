package com.example.demotest.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private String author;
    private String title;
}
