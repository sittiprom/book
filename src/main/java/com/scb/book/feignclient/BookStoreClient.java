package com.scb.book.feignclient;

import com.scb.book.constant.BookConstant;
import com.scb.book.model.response.BookDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = BookConstant.BOOK_SERVICE_NAME, url = "${book.scb.url}")
public interface BookStoreClient {

    @GetMapping(path = BookConstant.BOOK_LIST_URL)
    List<BookDetailResponse> getBookList();

    @GetMapping(path = BookConstant.BOOK_RECOMMENDATION_LIST_URL)
    List<BookDetailResponse> getBookRecommendList();
}
