package com.yuvaintern.blogapi.repository;
import com.yuvaintern.blogapi.model.User; import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User,Long>{ boolean existsByEmailIgnoreCase(String email); }