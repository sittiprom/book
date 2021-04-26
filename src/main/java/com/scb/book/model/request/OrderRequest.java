package com.scb.book.model.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<Integer> orders;

}
