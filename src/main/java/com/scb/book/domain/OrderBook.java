package com.scb.book.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="order_book")
public class OrderBook implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id",nullable = false)
    private User user;

    @OneToMany(mappedBy = "orderBook")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private List<OrderBookDetail> orderBookDetail;

}
