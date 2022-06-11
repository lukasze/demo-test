package com.example.demotest.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    // Jeśli utworzymy new ObjectMapper() nie uda się zmapować np. LocalDate
    // Mapowanie Java <-> JSON
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

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

    @Test
    @DisplayName("GET /books -> all Books (4)")
    void whenGetBooks_thenReturnAllBooks() throws Exception {

        var endpointURL = "/books";
        MvcResult mvcResult = mockMvc
                .perform(
                        get(endpointURL)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String booksASJSON = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        // objectMapper.readValue("{JSON}", TypJavy.class)
        List<Book> books = objectMapper.readValue(booksASJSON, new TypeReference<>() {});

        assertAll( "zwrócone wszystkie książki z repo",
                () -> assertEquals(4, books.size()),
                () -> assertEquals("Miłosz Brzeziński", books.get(0).getAuthor()),
                () -> assertEquals("Yuval Noah Harari", books.get(1).getAuthor()),
                () -> assertEquals("Andrzej Leder", books.get(2).getAuthor()),
                () -> assertEquals("MICHAEL POLLAN", books.get(3).getAuthor())
        );
    }

    @Test
    @DisplayName("POST /books -> 200")
    // Supermoc - czyszczenie kontekstu Spring'a (np. baza H2)
    @DirtiesContext
    void whenPOSTBooks_thenReturn200AndAddBookToRepo() throws Exception {
        // given
        var endpointURL = "/books";
        var author = "Simon Singh";
        var title = "The Code Book";
        var newBook = Book.builder().author(author).title(title).build();
        var newBookAsJSON = objectMapper.writeValueAsString(newBook);
        var initialListSize = bookRepository.getAll().size();
        // when
       mockMvc
                .perform(
                        post(endpointURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newBookAsJSON)
                )
                .andDo(print())
                .andExpect(status().is(200));
        // then
        assertAll("should add one book",
                () -> assertEquals(initialListSize + 1, bookRepository.getAll().size()),
                () -> assertEquals(author, bookRepository.getAll().get(4).getAuthor()),
                () -> assertEquals(title, bookRepository.getAll().get(4).getTitle())
        );
    }


}