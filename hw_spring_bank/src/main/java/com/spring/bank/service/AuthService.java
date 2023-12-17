package com.spring.bank.service;


import com.spring.bank.model.User;

public interface AuthService {
    User login(String username, String password);

    User register(User user);
}
