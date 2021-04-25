package com.scb.book.service;

import com.scb.book.domain.Book;
import com.scb.book.domain.OrderBook;
import com.scb.book.domain.OrderBookDetail;
import com.scb.book.domain.User;
import com.scb.book.model.response.request.OrderRequest;
import com.scb.book.repository.BookRepository;
import com.scb.book.repository.OrderBookDetailRepository;
import com.scb.book.repository.OrderBookRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrderService {

    private final OrderBookRepository orderBookRepository;
    private final BookRepository bookRepository;
    private final OrderBookDetailRepository bookDetailRepository;
    Logger logger = LogManager.getLogger(OrderService.class);

    public OrderService(OrderBookRepository orderBookRepository, BookRepository bookRepository, OrderBookDetailRepository bookDetailRepository) {

        this.orderBookRepository = orderBookRepository;
        this.bookRepository = bookRepository;
        this.bookDetailRepository = bookDetailRepository;
    }

    public String orderBookCreation(OrderRequest request, User user) {
        List<Book> books = bookRepository.findAllById(request.getOrders());
        List<OrderBookDetail> bookDetails = new ArrayList<>();
        String priceValue = null;
        try {
            if (!books.isEmpty()) {
                OrderBook orderBook = new OrderBook();
                BigDecimal price = BigDecimal.valueOf(0);
                orderBook.setUser(user);
                orderBook = orderBookRepository.saveAndFlush(orderBook);
                for (Book book : books) {
                    OrderBookDetail orderBookDetail = new OrderBookDetail();
                    orderBookDetail.setBook(book);
                    orderBookDetail.setOrderBook(orderBook);
                    bookDetails.add(orderBookDetail);
                    price = price.add(book.getPrice());
                }
                bookDetailRepository.saveAll(bookDetails);
                orderBook.setOrderBookDetail(bookDetails);
                orderBookRepository.save(orderBook);
                priceValue = String.valueOf(price);
            }

        } catch (Exception e) {
            logger.error(" Error during create order {}", e.getMessage());
        }
      return priceValue;
    }
}
