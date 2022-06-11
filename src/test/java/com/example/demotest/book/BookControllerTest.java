package com.example.demotest.book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Supermoc [Spring]: uruchom Spring'a przed testem (cały kontekst)
@SpringBootTest
// Supermoc - Spring będzie wiedział, jak utworzyć obiekt typu MockMvc
@AutoConfigureMockMvc
class BookControllerTest {

    /*
        W teście wstrzykiwanie przez pole jak najbardziej OK.
        MockMvc dostarcza metody do testów endpointów Spring MVC
     */
    @Autowired
    private MockMvc mockMvc;

    // Supermoc[JUnit]: test
    @Test
    // Supermoc[JUnit]: w liście wyświetlanych testów info z @DisplayName, amiast nazwy testu
    @DisplayName("GET /books -> HTTP 200")
    void whenGetBooks_thenReturnHTTP200() throws Exception {
        /*
            Nie zostawiamy pustych testów!
            Wyjątki np. contextLoads();
        fail();
         */
        var endpointURL = "/books";
        mockMvc
                // Wyślij request HTTP GET na endpoint /books
                .perform(
                        get(endpointURL)
                )
                // dodatkowe logowanie
                .andDo(print())
                // Oczekuj HTTP 200
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /books -> HTTP 404")
    void whenGetBook_thenReturnHTTP404() throws Exception {

        var endpointURL = "/book";
        mockMvc
                .perform(
                        get(endpointURL)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}