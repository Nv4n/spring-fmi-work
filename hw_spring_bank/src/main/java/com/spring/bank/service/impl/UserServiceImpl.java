package com.spring.bank.service.impl;

import com.spring.bank.dao.UserRepository;
import com.spring.bank.exception.EntityNotFoundException;
import com.spring.bank.exception.InvalidEntityException;
import com.spring.bank.model.User;
import com.spring.bank.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public Collection<User> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID{ %s } not found", id)));

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User %s not found", username)));
    }

    @Override
    public User createUser(User user) {
        userRepo.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new InvalidEntityException(
                    String.format("User %s already exist", user.getUsername()));
        });
        user.setCreatedAt(new Date());
        user.setModifiedAt(new Date());
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        if (!user.getUsername().equals(old.getUsername())) {
            throw new InvalidEntityException("Username of user couldn't be changed");
        }
        user.setModifiedAt(new Date());
        return userRepo.save(user);
    }

    @Override
    public User deleteUser(UUID id) {
        User old = userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        String.format("User with ID{ %s } not found.", id)));
        userRepo.deleteById(id);
        return old;
    }

    @Override
    public Long getUsersCount() {
        return userRepo.count();
    }

    @Override
    @Transactional
    public List<User> createUserBatch(List<User> users) {
        return users.stream()
                .map(this::createUser)
                .collect(Collectors.toList());
    }
}
