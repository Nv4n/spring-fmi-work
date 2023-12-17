package com.fmi2023.exam01.provider.impl;

import com.fmi2023.exam01.dao.CommentRepository;
import com.fmi2023.exam01.model.Comment;
import com.fmi2023.exam01.provider.CommentProvider;
import com.fmi2023.exam01.qualifier.Default;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("commentProvider")
@Default
public class RepoCommentProvider implements CommentProvider {
    private CommentRepository commentRepository;

    public static RepoCommentProvider create(CommentRepository commentRepository) {
        var provider = new RepoCommentProvider();
        provider.setCommentRepository(commentRepository);
        return provider;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Integer getCommentCount() {
        return commentRepository.getEntityCount();
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.create(comment);
    }

    @Inject
    public void setCommentRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
