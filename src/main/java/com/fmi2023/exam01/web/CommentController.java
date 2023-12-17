package com.fmi2023.exam01.web;

import com.fmi2023.exam01.model.Comment;
import com.fmi2023.exam01.provider.CommentProvider;
import com.fmi2023.exam01.qualifier.Default;
import jakarta.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Inject
    @Default
    private CommentProvider commentProvider;

    @GetMapping
    public List<Comment> getAllComments() {
        return commentProvider.getAllComments();
    }

    @GetMapping("/{commentId}")
    public Optional<Comment> getAllComments(@PathVariable Long commentId) {
        return commentProvider.getCommentById(commentId);
    }

    @PostMapping
    public ResponseEntity<Comment> createPost(@RequestBody Comment comment) {
        var created = commentProvider.createComment(comment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(location).body(created);
    }

}
