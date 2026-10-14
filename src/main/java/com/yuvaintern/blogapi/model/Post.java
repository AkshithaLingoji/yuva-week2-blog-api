package com.yuvaintern.blogapi.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="posts") public class Post {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false,length=180) private String title;
@Column(nullable=false,length=10000) private String content;
@ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="author_id",nullable=false) private User author;
@Column(nullable=false,updatable=false) private Instant createdAt; @Column(nullable=false) private Instant updatedAt;
protected Post(){} public Post(String t,String c,User a){title=t;content=c;author=a;}
@PrePersist void create(){Instant n=Instant.now();createdAt=n;updatedAt=n;} @PreUpdate void updateTime(){updatedAt=Instant.now();}
public void update(String t,String c){title=t;content=c;} public Long getId(){return id;} public String getTitle(){return title;} public String getContent(){return content;} public User getAuthor(){return author;} public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
}