package com.yuvaintern.blogapi.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="comments") public class Comment {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false,length=2000) private String content;
@ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="post_id",nullable=false) private Post post;
@ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="author_id",nullable=false) private User author;
@Column(nullable=false,updatable=false) private Instant createdAt; @Column(nullable=false) private Instant updatedAt;
protected Comment(){} public Comment(String c,Post p,User a){content=c;post=p;author=a;}
@PrePersist void create(){Instant n=Instant.now();createdAt=n;updatedAt=n;} @PreUpdate void updateTime(){updatedAt=Instant.now();}
public void updateContent(String c){content=c;} public Long getId(){return id;} public String getContent(){return content;} public Post getPost(){return post;} public User getAuthor(){return author;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}