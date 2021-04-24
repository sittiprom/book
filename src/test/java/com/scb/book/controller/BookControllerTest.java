package com.scb.book.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.book.model.response.BookDetailResponse;
import com.scb.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class BookControllerTest {
    @Mock
    BookService bookService;
    @InjectMocks
    BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookList() {
        List<BookDetailResponse> bookDetailResponseList = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            bookDetailResponseList = mapper.readValue(Paths.get("src/test/resources/book_list_from_service.json")
                    .toFile(), new TypeReference<List<BookDetailResponse>>() {});
            when(bookService.getBookList()).thenReturn(bookDetailResponseList);

        } catch (Exception exception){

        }
        ResponseEntity<List<BookDetailResponse>> result = bookController.getBookList();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    void testGetBookListWithNotFound() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            when(bookService.getBookList()).thenReturn(null);

        } catch (Exception exception){

        }
        ResponseEntity<List<BookDetailResponse>> result = bookController.getBookList();
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getStatusCode().value());
    }
}
