package com.omair.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.omair.Models.Category;
import com.omair.Models.Posts;
import com.omair.Models.User;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.PostDto;
import com.omair.Payloads.PostResponse;
import com.omair.Repository.CategoryRepository;
import com.omair.Repository.PostsRepository;
import com.omair.Repository.UserRepository;
import com.omair.exception.ResourceNotFoundException;

@Service
public class PostsService {
    @Autowired
    private PostsRepository postReop;

    @Autowired
    private CategoryRepository catRepo;

    @Autowired
    private UserRepository useRepo;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<PostDto> crePost(@RequestBody PostDto data, @PathVariable Long ui, @PathVariable Long ci) {
        Category c = catRepo.findById(ci)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found: " + ci));

        User us = useRepo.findById(ui)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found: " + ui));

        Posts post = mapper.map(data, Posts.class);
        post.setCategId(c);
        post.setUserId(us);
        post.setAddDate(new Date());
        post.setImageName("temp.png");
        Posts newPost = postReop.save(post);
        return new ResponseEntity<>(mapper.map(newPost, PostDto.class), HttpStatus.CREATED);
    }

    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Pageable p;
        if(sortDir.equalsIgnoreCase("asc")){
            p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        }else{
            p=  PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        }
        Page<Posts> pagePost = postReop.findAll(p); // it will give pageNum=0 then 5 post on page2 next 5post

        // List<Posts> posts = postReop.findAll(); without pagination
        List<PostDto> Allposts = pagePost.stream().map(e -> mapper.map(e, PostDto.class)).collect(Collectors.toList());
        PostResponse postRes = new PostResponse();
        postRes.setContent(Allposts);
        postRes.setPageNumber(pagePost.getNumber());
        postRes.setPageSize(pagePost.getSize());
        postRes.setLastPage(pagePost.isLast());
        postRes.setTotalRecords(pagePost.getTotalElements());
        postRes.setTotalPages(pagePost.getTotalPages());
        return postRes;
    }

    public PostDto postById(@PathVariable Long ui) {
        Posts post = postReop.findById(ui)
                .orElseThrow(() -> new ResourceNotFoundException("Id not found: " + ui));
        return mapper.map(post, PostDto.class);
    }

    public ResponseEntity<List<PostDto>> postByUser(@PathVariable Long id) {
        User user = useRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + id));
        List<Posts> userPost = postReop.findByUserId(user);
        return new ResponseEntity<>(
                userPost.stream().map(e -> mapper.map(e, PostDto.class)).collect(Collectors.toList()),
                HttpStatus.FOUND);

    }

    public List<PostDto> postByCateg(@PathVariable Long id) {
        Category cat = catRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categ Id not found: " + id));
        List<Posts> userPost = postReop.findByCategId(cat);
        return userPost.stream().map(e -> mapper.map(e, PostDto.class)).collect(Collectors.toList());
    }

    public ResponseEntity<Apiresponse> delPost(@PathVariable Long id) {
        Posts exist = postReop.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with this id: " + id));
        postReop.delete(exist);
        return new ResponseEntity<>(new Apiresponse("Deleted success", true), HttpStatus.OK);
    }

    public PostDto updatePost(@RequestBody PostDto post, @PathVariable Long id) {
        Posts exist = postReop.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with this id: " + id));
        exist.setPostContent(post.getPostContent());
        exist.setPostTitle(post.getPostTitle());
        exist.setImageName(post.getImageName());
        postReop.save(exist);
        return mapper.map(exist, PostDto.class);
    }

    //search

    public List<PostDto> searchByTitle(@PathVariable String st){
        List<Posts> posts = postReop.findByPostTitleContaining(st);
        return posts.stream().map(e-> mapper.map(e, PostDto.class)).collect(Collectors.toList());
    }
}
