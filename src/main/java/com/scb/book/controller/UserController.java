package com.scb.book.controller;

import com.scb.book.domain.Book;
import com.scb.book.domain.User;
import com.scb.book.model.response.OrderResponse;
import com.scb.book.model.response.UserResponse;
import com.scb.book.model.response.request.OrderRequest;
import com.scb.book.model.response.request.UserRequest;
import com.scb.book.repository.BookRepository;
import com.scb.book.repository.UserRepository;
import com.scb.book.service.OrderService;
import com.scb.book.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderService orderService;

    public UserController(UserService userService, UserRepository userRepository, BookRepository bookRepository, OrderService orderService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.orderService = orderService;
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userName") String userName) {
        try {
            UserResponse userResponse = userService.getUser(userName);
            if(userResponse != null){
                return ResponseEntity.ok(userResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @PostMapping(value = "/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {

        try{
            Optional<User> user = userRepository.findByUserName(userRequest.getUserName());
            if(user.isPresent()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            UserResponse newUser = userService.saveUser(userRequest);
            if(newUser !=null){
                return ResponseEntity.ok(newUser);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @PostMapping(value = "/user/orders")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request){
        Optional<User> user = userRepository.findByUserName(request.getUserName());
        String price = null;
        OrderResponse orderResponse = new OrderResponse();
        List<Book> books = bookRepository.findAllById(request.getOrders());
        if(!user.isPresent()){
            orderResponse.setError("There is no user name in DB");
            return ResponseEntity.ok().body(orderResponse);
        }
        if(books.isEmpty()){
            orderResponse.setError("There is no booking list in DB");
            return ResponseEntity.ok().body(orderResponse);
        }
        price = orderService.orderBookCreation(request,user.get());
        if(!price.isEmpty()){
            orderResponse.setPrice(price);
            return ResponseEntity.ok(orderResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @RequestMapping(value = "/user/{userName}", method = DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String userName) {
           Boolean isSuccess = userService.deleteUser(userName);
           if(isSuccess){
               return ResponseEntity.ok().body("success");
           } else {
               return ResponseEntity.ok().body("failed");
           }

    }

}
