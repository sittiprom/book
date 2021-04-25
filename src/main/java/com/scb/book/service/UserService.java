package com.scb.book.service;

import com.scb.book.domain.Book;
import com.scb.book.domain.OrderBook;
import com.scb.book.domain.OrderBookDetail;
import com.scb.book.domain.User;
import com.scb.book.model.response.UserResponse;
import com.scb.book.repository.UserRepository;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUser(String userName){
        Optional<User> userResult = userRepository.findByUserName(userName);
        List<Integer> books = new ArrayList<>();
        UserResponse userResponse = null;

        if(userResult.isPresent()){
            User user = userResult.get();

            List<Integer> finalBooks = books;
            if(!user.getOrderBooks().isEmpty()){
                List<OrderBook> orderBooks = user.getOrderBooks();
                 orderBooks.stream().forEach(orderBook -> {
                    orderBook.getOrderBookDetail().stream().forEach(orderBookDetail -> {
                        finalBooks.add(orderBookDetail.getBook().getId());
                    });
                });

            }
            userResponse  = new UserResponse(user,finalBooks);

        }

        return  userResponse;

    }


}
