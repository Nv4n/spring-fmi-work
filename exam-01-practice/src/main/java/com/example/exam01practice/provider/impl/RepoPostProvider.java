package com.example.exam01practice.provider.impl;

import com.example.exam01practice.dao.PostRepository;
import com.example.exam01practice.model.Post;
import com.example.exam01practice.model.PostDTO;
import com.example.exam01practice.provider.PostProvider;
import com.example.exam01practice.qualifer.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RepoBacked
@Service("repoProvider")
public class RepoPostProvider implements PostProvider {
    private PostRepository postRepo;

    public static RepoPostProvider create(PostRepository postRepo) {
        var provider = new RepoPostProvider();
        provider.setPostRepository(postRepo);
        return provider;
    }

    @Override
    public List<Post> getPosts() {
        return postRepo.findAll();
    }

    @Override
    public Post createPost(PostDTO postDTO) {
        return postRepo.create(postDTO);
    }

    @Override
    public Optional<Post> updatePost(Post post) {
        return postRepo.update(post);
    }

    @Override
    public Optional<Post> deletePost(Long id) {
        return postRepo.deleteById(id);
    }

    @Inject
    public void setPostRepository(PostRepository postRepo) {
        this.postRepo = postRepo;
    }
}
