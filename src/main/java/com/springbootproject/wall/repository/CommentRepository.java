package com.springbootproject.wall.repository;

import com.springbootproject.wall.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>{
}
