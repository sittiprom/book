package com.scb.book.service;

import com.scb.book.domain.OrderBook;
import com.scb.book.domain.User;
import com.scb.book.model.response.UserResponse;
import com.scb.book.model.response.request.UserRequest;
import com.scb.book.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
                    orderBooks.stream().forEach(orderBook ->
                        orderBook.getOrderBookDetail().stream().forEach(orderBookDetail ->
                            finalBooks.add(orderBookDetail.getBook().getId())
                        )
                    );

                }
                List<Integer> removeDuplicateBooks = finalBooks;
                removeDuplicateBooks = removeDuplicateBooks.stream().distinct().collect(Collectors.toList());
                userResponse  = new UserResponse(user,removeDuplicateBooks);

            }

        }catch (Exception ex){
            logger.error(" Error during get user {}" ,ex.getMessage());

        }

        return  userResponse;

    }

    public UserResponse saveUser(UserRequest userRequest){
        UserResponse userResponse = null;
        User user = new User();
        try {
            user.setUserName(userRequest.getUserName());
            user.setPassword(userRequest.getPassword());
            user.setName(userRequest.getName());
            user.setSurname(userRequest.getSurname());
            user.setDateOfBirth(userRequest.getDateOfBirth());
            user = userRepository.save(user);
            userResponse = new UserResponse(user,null);

        }catch (Exception ex){
            logger.error(" Error during get user {}" ,ex.getMessage());

        }

      return userResponse;
    }

    public Boolean deleteUser(String userName){
        Boolean isSuccess = Boolean.FALSE;
        try {
            Optional<User> userResult = userRepository.findByUserName(userName);
            if(userResult.isPresent()){
                userRepository.delete(userResult.get());
                isSuccess = Boolean.TRUE;
            }

        }catch (Exception ex){
            logger.error(" Error during deleting user {}" , ex.getMessage());
        }
        return isSuccess;

    }




}
