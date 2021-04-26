package com.scb.book.service;

import com.scb.book.domain.User;
import com.scb.book.model.UserLogin;
import com.scb.book.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoginService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if(user.isPresent()){
            return new UserLogin(user.get());

        }else {
            throw   new UsernameNotFoundException(
                    String.format("user with username %s not found", username));
        }
    }
}
