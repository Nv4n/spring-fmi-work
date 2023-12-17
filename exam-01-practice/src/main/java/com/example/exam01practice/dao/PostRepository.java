package com.example.exam01practice.dao;

import com.example.exam01practice.model.Post;
import com.example.exam01practice.model.PostDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends BaseRepository<Long, Post> {
    List<Post> findByTags(Set<String> tags);

    Post create(PostDTO postDto);
}
