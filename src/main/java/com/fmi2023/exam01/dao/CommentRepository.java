package com.fmi2023.exam01.dao;

import com.fmi2023.exam01.model.Comment;
import com.fmi2023.exam01.model.Identifiable;

import java.util.Optional;

public interface CommentRepository extends BaseRepository<Long, Comment> {
    public Optional<Comment> findByAuthorEmail(String authorEmail);
}
