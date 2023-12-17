package com.example.exam01practice.provider.impl;

import com.example.exam01practice.model.Gender;
import com.example.exam01practice.model.Post;
import com.example.exam01practice.model.PostDTO;
import com.example.exam01practice.model.User;
import com.example.exam01practice.provider.PostProvider;
import com.example.exam01practice.qualifer.Default;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Default
@Service("defaultProvider")
public class DefaultPostProvider implements PostProvider {
    @Override
    public List<Post> getPosts() {
        var user = new User(1L, "default@email.bg", "pass", Gender.Male);
        return List.of(
                new Post(1L, "T1", "B1", user, Set.of("tag1", "tag2"), 3),
                new Post(2L, "T2", "B2", user, Set.of("tag1", "tag3"), 8),
                new Post(3L, "T3", "B3", user, Set.of("tag1", "tag6"), 12)
        );
    }

    @Override
    public Post createPost(PostDTO postDTO) {
        return null;
    }

    @Override
    public Optional<Post> updatePost(Post post) {
        return Optional.empty();
    }

    @Override
    public Optional<Post> deletePost(Long id) {
        return Optional.empty();
    }
}
