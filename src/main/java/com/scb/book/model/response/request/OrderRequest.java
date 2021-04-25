package com.scb.book.model.response.request;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<Integer> orders;
    private String userName;
}
