package com.springbootproject.wall.service;

import com.springbootproject.wall.dto.PostDto;
import com.springbootproject.wall.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy);

    PostDto getPostById(long id);

    PostDto updatePost(PostDto postDto, long id);

    void deletePostByID(long id);
}

