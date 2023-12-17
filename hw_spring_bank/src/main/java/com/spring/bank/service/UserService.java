package com.spring.bank.service;

import com.spring.bank.model.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface UserService {
    Collection<User> getUsers();

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    User deleteUser(UUID id);

    Long getUsersCount();

    List<User> createUserBatch(List<User> users);
}
