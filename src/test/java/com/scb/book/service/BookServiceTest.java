package com.scb.book.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.book.feignclient.BookStoreClient;
import com.scb.book.model.response.BookDetailResponse;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    BookStoreClient bookStoreClient;
    @Mock
    Logger log;
    @InjectMocks
    BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookList() {
        List<BookDetailResponse> bookRecommendList = null;
        List<BookDetailResponse> bookList = null;

        try{
            ObjectMapper mapper = new ObjectMapper();
            bookRecommendList = mapper.readValue(Paths.get("src/test/resources/book_recommend_list.json")
                    .toFile(), new TypeReference<List<BookDetailResponse>>() {});
            bookList = mapper.readValue(Paths.get("src/test/resources/book_list.json")
                    .toFile(),new TypeReference<List<BookDetailResponse>>() {});
            when(bookStoreClient.getBookList()).thenReturn(bookList);
            when(bookStoreClient.getBookRecommendList()).thenReturn(bookRecommendList);

        }catch (Exception exception){

        }
        List<BookDetailResponse> result = bookService.getBookList();
        List<BookDetailResponse> resultRecommendList = result.stream().filter(bookDetailResponse -> Boolean.TRUE.equals(bookDetailResponse
                .getIsRecommended())).collect(Collectors.toList());

        Assertions.assertEquals(bookList.size(), result.size());
        Assertions.assertEquals(Boolean.TRUE,result.get(0).getIsRecommended());
        Assertions.assertEquals(Boolean.TRUE,result.get(1).getIsRecommended());
        Assertions.assertEquals(bookRecommendList.size(),resultRecommendList.size());
    }
    @Test
    void testGetBookListNoRecommendList() {
        List<BookDetailResponse> bookRecommendList = null;
        List<BookDetailResponse> bookList = null;

        try{
            ObjectMapper mapper = new ObjectMapper();
            bookList = mapper.readValue(Paths.get("src/test/resources/book_list.json")
                    .toFile(),new TypeReference<List<BookDetailResponse>>() {});
            when(bookStoreClient.getBookList()).thenReturn(bookList);
            when(bookStoreClient.getBookRecommendList()).thenReturn(new ArrayList<>());



        }catch (Exception exception){

        }
        List<BookDetailResponse> result = bookService.getBookList();
        List<BookDetailResponse> recommendList = result.stream().filter(bookDetailResponse -> Boolean.TRUE.equals(bookDetailResponse
                .getIsRecommended())).collect(Collectors.toList());
        Assertions.assertEquals(bookList.size(), result.size());
        Assertions.assertEquals(Boolean.FALSE,result.get(0).getIsRecommended());
        Assertions.assertEquals(Boolean.FALSE,result.get(1).getIsRecommended());
        Assertions.assertEquals(0,recommendList.size());
    }

    @Test
    void testGetBookListWithFiveRecommend() {
        List<BookDetailResponse> bookRecommendList = null;
        List<BookDetailResponse> bookList = null;

        try{
            ObjectMapper mapper = new ObjectMapper();
            bookRecommendList = mapper.readValue(Paths.get("src/test/resources/book_recommend_list_with_five_item.json")
                    .toFile(), new TypeReference<List<BookDetailResponse>>() {});
            bookList = mapper.readValue(Paths.get("src/test/resources/book_list.json")
                    .toFile(),new TypeReference<List<BookDetailResponse>>() {});
            when(bookStoreClient.getBookList()).thenReturn(bookList);
            when(bookStoreClient.getBookRecommendList()).thenReturn(bookRecommendList);

        }catch (Exception exception){

        }
        List<BookDetailResponse> result = bookService.getBookList();
        List<BookDetailResponse> resultRecommendList = result.stream().filter(bookDetailResponse -> Boolean.TRUE.equals(bookDetailResponse
                .getIsRecommended())).collect(Collectors.toList());

        Assertions.assertEquals(bookList.size(), result.size());
        Assertions.assertEquals(Boolean.TRUE,result.get(0).getIsRecommended());
        Assertions.assertEquals(Boolean.TRUE,result.get(1).getIsRecommended());
        Assertions.assertEquals(Boolean.TRUE,result.get(2).getIsRecommended());
        Assertions.assertEquals(Boolean.TRUE,result.get(3).getIsRecommended());
        Assertions.assertEquals(Boolean.TRUE,result.get(4).getIsRecommended());
        Assertions.assertEquals(bookRecommendList.size(),resultRecommendList.size());
    }
}

