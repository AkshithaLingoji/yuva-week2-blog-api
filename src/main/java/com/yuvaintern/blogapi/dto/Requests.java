package com.yuvaintern.blogapi.dto;
import jakarta.validation.constraints.*;
public final class Requests { private Requests(){}
public record Register(@NotBlank @Size(min=2,max=100) String name,@NotBlank @Email @Size(max=254) String email,@NotBlank @Size(min=8,max=72) String password){}
public record PostCreate(@NotBlank @Size(min=3,max=180) String title,@NotBlank @Size(max=10000) String content,@NotNull Long authorId){}
public record PostUpdate(@NotBlank @Size(min=3,max=180) String title,@NotBlank @Size(max=10000) String content){}
public record CommentCreate(@NotBlank @Size(max=2000) String content,@NotNull Long authorId){}
public record CommentUpdate(@NotBlank @Size(max=2000) String content){}
}