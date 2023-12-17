package com.spring.bank.service.impl;

import com.spring.bank.model.User;
import com.spring.bank.service.AuthService;
import com.spring.bank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;

    @Override
    public User login(String username, String password) {
        User user = userService.getUserByUsername(username);
        
        if (user.getPasswordHash().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public User register(User user) {
        return userService.createUser(user);
    }
}
