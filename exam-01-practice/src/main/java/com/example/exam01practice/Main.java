package com.example.exam01practice;

import com.example.exam01practice.dao.PostRepository;
import com.example.exam01practice.dao.UserRepository;
import com.example.exam01practice.model.Gender;
import com.example.exam01practice.model.PostDTO;
import com.example.exam01practice.model.User;
import com.example.exam01practice.presenter.PostPresenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Set;

public class Main {
    static final List<PostDTO> repoPosts = List.of(
            new PostDTO("Post1", "Content1", "email@abv.bg", Set.of("tag1", "tag2"), 23),
            new PostDTO("Post2", "Content2", "email@abv.bg", Set.of("tag5", "tag2"), 13),
            new PostDTO("Post2", "Content3", "test@abv.bg", Set.of("tag3", "tag2"), 53)
    );
    static final List<User> repoUsers = List.of(
            new User("test@abv.bg", "Pass", Gender.Male),
            new User("email@abv.bg", "Pass2", Gender.Female),
            new User("goshop@abv.bg", "Pass333", Gender.Other)
    );

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("com.example.exam01practice");
        UserRepository userRepo = ctx.getBean(UserRepository.class);
        repoUsers.forEach(userRepo::create);
        System.out.println(userRepo.findAll());
        PostRepository postRepo = ctx.getBean(PostRepository.class);
        repoPosts.forEach(postRepo::create);

        PostPresenter presenter = ctx.getBean(PostPresenter.class);
        presenter.present();
    }
}
