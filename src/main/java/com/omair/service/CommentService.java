package com.omair.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.omair.Models.Comment;
import com.omair.Models.Posts;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.CommentDto;
import com.omair.Repository.CommentRepository;
import com.omair.Repository.PostsRepository;
import com.omair.exception.ResourceNotFoundException; 

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostsRepository postRepo;

    @Autowired
    private ModelMapper mapper;
    public List<CommentDto> getAllComment(){
        List<Comment> cm = this.commentRepo.findAll();
       return cm.stream().map(e-> mapper.map(e, CommentDto.class)).collect(Collectors.toList());
        
    }

    public CommentDto createComment(@PathVariable Long postId, @RequestBody CommentDto content){
        Posts post = this.postRepo.findById(postId)
        .orElseThrow(()-> new ResourceNotFoundException("Post not exits"+postId));
        Comment cont = mapper.map(content, Comment.class);
        cont.setUser(post.getUserId());
        cont.setPost(post);
        return  mapper.map(commentRepo.save(cont), CommentDto.class);
    }

    public Apiresponse deleteComment(@PathVariable Long id){
        Comment cm = commentRepo.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Comment not found with id: "+id));
        commentRepo.delete(cm);
        return new Apiresponse("Deleted", true);
    }

    public CommentDto getById(@PathVariable Long id){
        Comment cm = commentRepo.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Comment not found with id: "+id));
        commentRepo.delete(cm);
        return mapper.map(cm, CommentDto.class);
    }
}
