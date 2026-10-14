package com.yuvaintern.blogapi.repository;
import com.yuvaintern.blogapi.model.Comment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface CommentRepository extends JpaRepository<Comment,Long>{ List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId); }