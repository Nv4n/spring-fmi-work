package com.example.exam01practice.dao.impl;

import com.example.exam01practice.dao.IdGenerator;
import com.example.exam01practice.dao.UserRepository;
import com.example.exam01practice.model.Gender;
import com.example.exam01practice.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userRepo")
public class UserRepoImpl extends BaseRepoImpl<Long, User> implements UserRepository {

    public UserRepoImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public List<User> findByGender(Gender gender) {
        return findAll()
                .stream()
                .filter(user -> user.getGender() == gender)
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll()
                .stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
