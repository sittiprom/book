package com.scb.book.model.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BookDetailResponse {
    private String authorName;
    private BigDecimal price;
    private String bookName;
    private String id;
    private Boolean isRecommended = Boolean.FALSE;

}

