package com.springbootproject.wall.service.impl;

import com.springbootproject.wall.dto.CommentDto;
import com.springbootproject.wall.entity.Comment;
import com.springbootproject.wall.entity.Post;
import com.springbootproject.wall.exception.ResourceNotFoundException;
import com.springbootproject.wall.repository.CommentRepository;
import com.springbootproject.wall.repository.PostRepository;
import com.springbootproject.wall.service.CommentService;
import org.springframework.stereotype.Service;

@Service
//configure CommentServiceImpl class as a spring bean and can inject it in other classes
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    //not using @Autowired annotation here because CommentServiceImpl class is configured a spring bean, and it has only one constructor
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository=commentRepository;
        this.postRepository= postRepository;
    }

    @Override
    public CommentDto createComment(long postID, CommentDto commentDto) {

        //convert DTO to entity
        Comment comment = mapToEntity(commentDto);

        //retrieve posts by  postid
        Post post= postRepository.findById(postID).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postID));

        //once we get the post then  we will pass post object to comment entity
        comment.setPost(post);

        //comment entity to DB
        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    //convert entity to DTO
    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto= new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());

        return commentDto;
    }

    //convert DTO to entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment= new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return comment;
    }

}
