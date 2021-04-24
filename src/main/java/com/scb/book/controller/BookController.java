package com.scb.book.controller;

import com.scb.book.model.response.BookDetailResponse;
import com.scb.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDetailResponse>> getBookList() {
        List<BookDetailResponse> bookList = bookService.getBookList();
        if(bookList != null && !bookList.isEmpty()){
            return ResponseEntity.ok(bookList);
        } else {
            return ResponseEntity.notFound().build();
        }

    }


}
