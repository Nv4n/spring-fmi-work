package com.fmi2023.exam01.provider;

import com.fmi2023.exam01.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentProvider {
    List<Comment> getAllComments();

    Optional<Comment> getCommentById(Long Id);

    Integer getCommentCount();

    Comment createComment(Comment comment);
}
