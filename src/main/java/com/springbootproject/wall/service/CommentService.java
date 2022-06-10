package com.springbootproject.wall.service;

import com.springbootproject.wall.dto.CommentDto;

public interface CommentService {
    CommentDto createComment(long postID, CommentDto commentDto);
}
