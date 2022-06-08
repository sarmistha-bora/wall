package com.springbootproject.wall.repository;

import com.springbootproject.wall.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
//no need to write anything because we will get all CRUD methods by extending JPA Repository
//no need for @Repository annotation because JpaRepository takes care of it internally
}
