package com.scb.book.service;

import com.scb.book.domain.OrderBook;
import com.scb.book.domain.User;
import com.scb.book.model.response.UserResponse;
import com.scb.book.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
public class UserService {
    private final UserRepository userRepository;
    Logger logger = LogManager.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getUser(String userName){
        Optional<User> userResult = userRepository.findByUserName(userName);
        List<Integer> books = new ArrayList<>();
        UserResponse userResponse = null;

        try {
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

        }catch (Exception ex){
            logger.error(" Error during get user {}" ,ex.getMessage());

        }

        return  userResponse;

    }

    public UserResponse saveUser(User user){
        UserResponse userResponse = null;
        try {

            user = userRepository.save(user);
            userResponse = new UserResponse(user,new ArrayList());

        }catch (Exception ex){
            logger.error(" Error during get user {}" ,ex.getMessage());

        }

      return userResponse;
    }


}
