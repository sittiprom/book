package com.scb.book.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookResponse  extends BookDetailResponse{
    private Boolean  isRecommended;

    public BookResponse(BookDetailResponse bookDetailResponse,Boolean isRecommended) {
        this.isRecommended = isRecommended;
        this.setAuthorName(bookDetailResponse.getAuthorName());
        this.setBookName(bookDetailResponse.getBookName());
        this.setPrice(bookDetailResponse.getPrice());
        this.setId(bookDetailResponse.getId());

    }
}
