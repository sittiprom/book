package com.scb.book.service;

import com.scb.book.feignclient.BookStoreClient;
import com.scb.book.model.response.BookDetailResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookStoreClient bookStoreClient;
     Logger log = LogManager.getLogger(BookService.class);

    @Autowired
    public BookService(BookStoreClient bookStoreClient) {
        this.bookStoreClient = bookStoreClient;
    }

    public List<BookDetailResponse> getBookList() {
        List<BookDetailResponse> bookDetailResponseList = null;
        try {
            bookDetailResponseList = bookStoreClient.getBookList();
            List<BookDetailResponse> bookRecommendList = bookStoreClient.getBookRecommendList();
            List<String> recommendIdList = bookRecommendList.stream()
                    .map(BookDetailResponse::getId).collect(Collectors.toList());

            if (!recommendIdList.isEmpty()) {
                for (BookDetailResponse bookDetailResponse : bookDetailResponseList) {
                    if (recommendIdList.contains(bookDetailResponse.getId())) {
                        bookDetailResponse.setIsRecommended(Boolean.TRUE);
                    }

                }
            }

        } catch (Exception ex) {
            log.info(" error {} " , ex.getMessage());
        }

        if(bookDetailResponseList != null && !bookDetailResponseList.isEmpty()){
            bookDetailResponseList = bookDetailResponseList.stream().sorted(Comparator.comparing(BookDetailResponse :: getIsRecommended,
                    Comparator.reverseOrder())).collect(Collectors.toList());
        }

        return bookDetailResponseList;
    }

}
