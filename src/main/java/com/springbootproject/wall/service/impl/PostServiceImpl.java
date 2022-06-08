package com.springbootproject.wall.service.impl;

import com.springbootproject.wall.dto.PostDto;
import com.springbootproject.wall.dto.PostResponse;
import com.springbootproject.wall.entity.Post;
import com.springbootproject.wall.exception.ResourceNotFoundException;
import com.springbootproject.wall.repository.PostRepository;
import com.springbootproject.wall.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

   private PostRepository postRepository;

   //if a class has spring bean, and it has only one constructor, then we can omit the @Autowired
   @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //Create an entity object , we will create a post object and set all DTO details into post object
       //convert DTO to entity
        Post post= mapToEntity(postDto);

        Post newPost = postRepository.save(post);//save this post entity into database using postRepository.save() method
        //save method returns a newPost in database
        //we send this save(post) details to RESTapi, hence we will create PostDto object and set all newPost details to PostDto

        //convert entity into DTO using private method
        PostDto postResponse= mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy) {

       //create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        //we pass this pageable instance to findAll method

       //get all post from DB
       // List<Post> posts= postRepository.findAll();

        //return type of pageable is page
        Page<Post> posts= postRepository.findAll(pageable);

        //get content from page object
        List<Post> listOfPosts =posts.getContent();

        //use stream method and map post entity to DTO
        //return listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        List<PostDto> content=listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setLast(posts.isLast());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getNumberOfElements());
        postResponse.setTotalPages(posts.getTotalPages());

        return postResponse;
   }

    @Override
    public PostDto getPostById(long id) {
       Post post =postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
       return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        //get post by id from DB
        //if post is nt there, throw exception
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);

        return mapToDTO(updatedPost);

    }

    @Override
    public void deletePostByID(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        postRepository.delete(post);

    }


    //method to convert DTO to entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post(); //Create an entity object \\ we have created a post object and set all DTO details into post object
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    //method to convert entity into DTO
    private PostDto mapToDTO(Post newPost){

        PostDto postResponse= new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());

        //return PostDto
        return postResponse;
    }
}
