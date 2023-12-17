package com.example.exam01practice.dao;

import com.example.exam01practice.model.Gender;
import com.example.exam01practice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<Long, User> {
    List<User> findByGender(Gender gender);

    Optional<User> findByEmail(String email);
}
