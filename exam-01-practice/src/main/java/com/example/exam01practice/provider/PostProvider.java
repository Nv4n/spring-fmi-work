package com.example.exam01practice.provider;

import com.example.exam01practice.model.Post;
import com.example.exam01practice.model.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostProvider {
    List<Post> getPosts();

    Post createPost(PostDTO postDTO);

    Optional<Post> updatePost(Post post);

    Optional<Post> deletePost(Long id);
}
