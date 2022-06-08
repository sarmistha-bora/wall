package com.springbootproject.wall.controller;

import com.springbootproject.wall.dto.PostDto;
import com.springbootproject.wall.dto.PostResponse;
import com.springbootproject.wall.entity.Post;
import com.springbootproject.wall.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //create blog post
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto),HttpStatus.CREATED);
    }

    //get all blog post
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue ="0", required = false )int pageNo,
            @RequestParam(value = "pageSize", defaultValue ="10", required = false )int pageSize,
            @RequestParam(value = "sortBy", defaultValue="id", required = false) String sortBy
    ){
        return postService.getAllPost(pageNo, pageSize, sortBy);
    }

    //get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));

    }

    //update post by id
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name ="id") long id){

        PostDto postResponse = postService.updatePost(postDto, id);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    //delete post REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id){

        postService.deletePostByID(id);

        return new ResponseEntity<>("Post Entity Deleted successfully", HttpStatus.OK);
    }
}
