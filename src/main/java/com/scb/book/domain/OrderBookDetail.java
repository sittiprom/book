package com.scb.book.domain;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class OrderBookDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    @ManyToOne
    @JoinColumn(name="order_book_id", referencedColumnName = "id",nullable = false)
    private OrderBook orderBook;

    @ManyToOne
    @JoinColumn(name="book_id", referencedColumnName = "id",nullable = false)
    private Book book;
    private Integer item;


}
