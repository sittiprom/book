package com.scb.book.repository;

import com.scb.book.domain.OrderBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderBookDetailRepository extends JpaRepository<OrderBookDetail,Integer> {
}
