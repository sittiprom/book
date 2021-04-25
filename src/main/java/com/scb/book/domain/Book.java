package com.scb.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Data
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id ;
    private  String name;
    private String author;
    private BigDecimal price;
    @OneToMany(mappedBy = "book")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<OrderBookDetail> orderBookDetails;

}
