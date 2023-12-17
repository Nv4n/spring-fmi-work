package com.example.exam01practice.presenter.impl;

import com.example.exam01practice.presenter.PostPresenter;
import com.example.exam01practice.provider.PostProvider;
import com.example.exam01practice.qualifer.Default;
import com.example.exam01practice.qualifer.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class ConsolePostPresenter implements PostPresenter {
    private PostProvider postProvider;

    @Inject
    public ConsolePostPresenter(@Default PostProvider postProvider) {
        this.postProvider = postProvider;
    }

    @Override
    public void present() {
        postProvider.getPosts().forEach(System.out::println);
    }
}
