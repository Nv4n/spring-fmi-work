package com.example.exam01practice.web;

import com.example.exam01practice.model.Post;
import com.example.exam01practice.model.PostDTO;
import com.example.exam01practice.provider.PostProvider;
import com.example.exam01practice.qualifer.RepoBacked;
import jakarta.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Inject
    @RepoBacked
    private PostProvider postProvider;

    @GetMapping
    public List<Post> getAllPosts() {
        return postProvider.getPosts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Post> createPost(@RequestBody PostDTO postDTO) {
        var created = postProvider.createPost(postDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping
    public Optional<Post> updatePost(@RequestBody Post post) {
        return postProvider.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public Optional<Post> deletePost(@PathVariable Long id) {
        return postProvider.deletePost(id);
    }
}
