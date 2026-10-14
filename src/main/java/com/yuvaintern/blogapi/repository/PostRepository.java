package com.yuvaintern.blogapi.repository;
import com.yuvaintern.blogapi.model.Post; import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<Post,Long>{}