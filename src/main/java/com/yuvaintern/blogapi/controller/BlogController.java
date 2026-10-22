
package com.yuvaintern.blogapi.controller;

import org.springframework.data.domain.PageRequest;
import com.yuvaintern.blogapi.dto.Requests.*;
import com.yuvaintern.blogapi.dto.Responses.*;
import com.yuvaintern.blogapi.exception.ApiExceptions;
import com.yuvaintern.blogapi.model.*;
import com.yuvaintern.blogapi.repository.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
public class BlogController {

    private final UserRepository users;
    private final PostRepository posts;
    private final CommentRepository comments;
    private final PasswordEncoder encoder;

    public BlogController(
            UserRepository users,
            PostRepository posts,
            CommentRepository comments,
            PasswordEncoder encoder) {
        this.users = users;
        this.posts = posts;
        this.comments = comments;
        this.encoder = encoder;
    }

    @PostMapping("/api/users/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public UserView register(@Valid @RequestBody Register req) {
        String email = req.email().trim().toLowerCase(Locale.ROOT);

        if (users.existsByEmailIgnoreCase(email)) {
            throw new ApiExceptions.Conflict(
                    "An account with this email already exists.");
        }

        User user = new User(
                req.name().trim(),
                email,
                encoder.encode(req.password()));

        return UserView.of(users.save(user));
    }

    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public PostView createPost(@Valid @RequestBody PostCreate req) {
        User author = user(req.authorId());

        Post post = new Post(
                req.title().trim(),
                req.content().trim(),
                author);

        return PostView.of(posts.save(post));
    }

    
@GetMapping("/api/posts")
@Transactional(readOnly = true)
public List<PostView> allPosts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {

    int safePage = Math.max(page, 0);
    int safeSize = Math.min(Math.max(size, 1), 100);

    return posts.findAll(PageRequest.of(safePage, safeSize))
            .map(PostView::of)
            .getContent();
}


    @GetMapping("/api/posts/{id}")
    @Transactional(readOnly = true)
    public PostView post(@PathVariable Long id) {
        return PostView.of(postEntity(id));
    }

    @PutMapping("/api/posts/{id}")
    @Transactional
    public PostView updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdate req) {

        Post post = postEntity(id);
        post.update(req.title().trim(), req.content().trim());

        return PostView.of(posts.save(post));
    }

    @DeleteMapping("/api/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deletePost(@PathVariable Long id) {
        Post post = postEntity(id);

        comments.deleteAll(
                comments.findByPostIdOrderByCreatedAtAsc(id));

        posts.delete(post);
    }

    @PostMapping("/api/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public CommentView createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreate req) {

        Comment comment = new Comment(
                req.content().trim(),
                postEntity(postId),
                user(req.authorId()));

        return CommentView.of(comments.save(comment));
    }

    @GetMapping("/api/posts/{postId}/comments")
    @Transactional(readOnly = true)
    public List<CommentView> commentsForPost(
            @PathVariable Long postId) {

        postEntity(postId);

        return comments.findByPostIdOrderByCreatedAtAsc(postId)
                .stream()
                .map(CommentView::of)
                .toList();
    }

    @GetMapping("/api/comments/{id}")
    @Transactional(readOnly = true)
    public CommentView comment(@PathVariable Long id) {
        return CommentView.of(commentEntity(id));
    }

    @PutMapping("/api/comments/{id}")
    @Transactional
    public CommentView updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentUpdate req) {

        Comment comment = commentEntity(id);
        comment.updateContent(req.content().trim());

        return CommentView.of(comments.save(comment));
    }

    @DeleteMapping("/api/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteComment(@PathVariable Long id) {
        comments.delete(commentEntity(id));
    }

    private User user(Long id) {
        return users.findById(id)
                .orElseThrow(() ->
                        new ApiExceptions.NotFound("User not found: " + id));
    }

    private Post postEntity(Long id) {
        return posts.findById(id)
                .orElseThrow(() ->
                        new ApiExceptions.NotFound("Post not found: " + id));
    }

    private Comment commentEntity(Long id) {
        return comments.findById(id)
                .orElseThrow(() ->
                        new ApiExceptions.NotFound("Comment not found: " + id));
    }
}
