
package com.yuvaintern.blogapi.dto;

import com.yuvaintern.blogapi.model.Comment;
import com.yuvaintern.blogapi.model.Post;
import com.yuvaintern.blogapi.model.User;

import java.time.Instant;

public final class Responses {

    private Responses() {
    }

    public record UserView(
            Long id,
            String name,
            String email,
            Instant createdAt) {

        public static UserView of(User user) {
            return new UserView(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCreatedAt());
        }
    }

    public record PostView(
            Long id,
            String title,
            String content,
            Long authorId,
            String authorName,
            Instant createdAt,
            Instant updatedAt) {

        public static PostView of(Post post) {
            return new PostView(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getAuthor().getId(),
                    post.getAuthor().getName(),
                    post.getCreatedAt(),
                    post.getUpdatedAt());
        }
    }

    public record CommentView(
            Long id,
            String content,
            Long postId,
            Long authorId,
            String authorName,
            Instant createdAt,
            Instant updatedAt) {

        public static CommentView of(Comment comment) {
            return new CommentView(
                    comment.getId(),
                    comment.getContent(),
                    comment.getPost().getId(),
                    comment.getAuthor().getId(),
                    comment.getAuthor().getName(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt());
        }
    }
}
