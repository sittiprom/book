package com.scb.book.controller;

import com.scb.book.domain.User;
import com.scb.book.model.response.UserResponse;
import com.scb.book.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody User user) {
        try{
            UserResponse newUser = userService.saveUser(user);
            if(newUser !=null && user.getId() != null){
                return ResponseEntity.ok(newUser);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

}
