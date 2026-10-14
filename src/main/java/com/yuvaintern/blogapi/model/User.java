package com.yuvaintern.blogapi.model;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="users", uniqueConstraints=@UniqueConstraint(columnNames="email")) public class User {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
@Column(nullable=false,length=100) private String name;
@Column(nullable=false,unique=true,length=254) private String email;
@Column(nullable=false,name="password_hash") private String passwordHash;
@Column(nullable=false,updatable=false) private Instant createdAt;
protected User(){} public User(String n,String e,String p){name=n;email=e;passwordHash=p;}
@PrePersist void create(){createdAt=Instant.now();}
public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public Instant getCreatedAt(){return createdAt;}
}