package com.fmi2023.exam01.init;

import com.fmi2023.exam01.model.Comment;
import com.fmi2023.exam01.provider.CommentProvider;
import com.fmi2023.exam01.qualifier.Default;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    private CommentProvider commentProvider;

    public DataInitializer(@Default CommentProvider commentProvider) {
        this.commentProvider = commentProvider;
    }

    @Override
    public void run(String... args) throws Exception {
        if (commentProvider.getCommentCount() == 0) {
            commentProvider.createComment(new Comment(1L, "/article/133242", "test@email.bg", "Very nice article"));
            commentProvider.createComment(new Comment(2L, "/article/342432", "test@email.bg", "I need more info above article's information"));
        }
    }
}