package com.yuvaintern.blogapi.dto;
import com.yuvaintern.blogapi.model.*; import java.time.Instant;
public final class Responses { private Responses(){}
public record UserView(Long id,String name,String email,Instant createdAt){public static UserView of(User u){return new UserView(u.getId(),u.getName(),u.getEmail(),u.getCreatedAt());}}
public record PostView(Long id,String title,String content,Long authorId,String authorName,Instant createdAt,Instant updatedAt){public static PostView of(Post p){return new PostView(p.getId(),p.getTitle(),p.getContent(),p.getAuthor().getId(),p.getAuthor().getName(),p.getCreatedAt(),p.getUpdatedAt());}}
public record CommentView(Long id,String content,Long postId,Long authorId,String authorName,Instant createdAt,Instant updatedAt){public static CommentView of(Comment c){return new CommentView(c.getId(),c.getContent(),c.getPost().getId(),c.getAuthor().getId(),c.getAuthor().getName(),c.getCreatedAt(),c.getUpdatedAt());}}
}