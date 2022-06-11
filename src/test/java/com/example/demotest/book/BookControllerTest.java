package com.example.demotest.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

// Supermoc [Spring]: uruchom Spring'a przed testem (cały kontekst)
@SpringBootTest
class BookControllerTest {
    // Supermoc[JUnit]: test
    @Test
    // Supermoc[JUnit]: w liście wyświetlanych testów info z @DisplayName, amiast nazwy testu
    @DisplayName("GET /books -> HTTP 200, lista książek {JSON}")
    public void whenGetBooks_thenReturn200AndAllBooks() {
        /*
            Nie zostawiamy pustych testów!
            Wyjątki np. contextLoads();
         */
        fail();
    }
}