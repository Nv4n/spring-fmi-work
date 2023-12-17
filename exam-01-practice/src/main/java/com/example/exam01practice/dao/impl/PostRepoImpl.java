package com.example.exam01practice.dao.impl;

import com.example.exam01practice.dao.IdGenerator;
import com.example.exam01practice.dao.PostRepository;
import com.example.exam01practice.dao.UserRepository;
import com.example.exam01practice.model.Post;
import com.example.exam01practice.model.PostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("postRepository")
public class PostRepoImpl extends BaseRepoImpl<Long, Post> implements PostRepository {
    private UserRepository userRepo;

    public PostRepoImpl(IdGenerator<Long> idGenerator) {
        super(idGenerator);
    }

    @Autowired
    void setUserRepository(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<Post> findByTags(Set<String> tags) {
        return findAll().stream().filter(post -> {
                    var tgs = Set.of(post.getTags());
                    tgs.retainAll(tags);
                    return !tgs.isEmpty();
                }
        ).toList();
    }

    @Override
    public Post create(PostDTO postDto) {
        var author = userRepo.findByEmail(postDto.getAuthorEmail());
        Post post = new Post(postDto.getId(),
                postDto.getTitle(),
                postDto.getBody(),
                author.get(),
                postDto.getTags(),
                postDto.getReactions());
        return create(post);
    }
}
