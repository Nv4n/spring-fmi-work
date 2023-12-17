package com.fmi2023.exam01.dao.impl;

import com.fmi2023.exam01.dao.BaseRepository;
import com.fmi2023.exam01.dao.CommentRepository;
import com.fmi2023.exam01.dao.IdGenerator;
import com.fmi2023.exam01.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("postRepository")
public class CommentRepositoryImpl extends BaseRepositoryImpl<Long, Comment> implements CommentRepository {

    public CommentRepositoryImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Override
    public Optional<Comment> findByAuthorEmail(String authorEmail) {
        return findAll().stream()
                .filter(c -> c.getAuthorEmail().equals(authorEmail))
                .findFirst();
    }
}
