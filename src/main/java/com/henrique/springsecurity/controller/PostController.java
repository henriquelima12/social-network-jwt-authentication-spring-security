package com.henrique.springsecurity.controller;

import com.henrique.springsecurity.dto.FeedDto;
import com.henrique.springsecurity.dto.FeedItemDto;
import com.henrique.springsecurity.dto.PostDto;
import com.henrique.springsecurity.entities.Post;
import com.henrique.springsecurity.entities.Role;
import com.henrique.springsecurity.repositories.PostRepository;
import com.henrique.springsecurity.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<FeedDto> listPosts(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                             @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        var posts = postRepository.findAll(
                PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "creationTimestamp"))
                .map(post ->
                        new FeedItemDto(post.getPostId(), post.getContent(), post.getUser().getUsername()));

        return ResponseEntity.ok(new FeedDto(posts.getContent(), pageNumber, pageSize, posts.getTotalPages(), posts.getTotalElements()));
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostDto post, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var newPost = new Post();
        newPost.setContent(post.content());
        newPost.setUser(user.get());
        postRepository.save(newPost);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id, JwtAuthenticationToken token) {
        var user = userRepository.findById(UUID.fromString(token.getName()));

        var post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        var isAdmin = user.get().getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if (isAdmin || post.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            postRepository.deleteById(id);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().build();
    }

}
