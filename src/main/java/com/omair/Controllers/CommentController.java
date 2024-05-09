package com.omair.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omair.Payloads.CommentDto;
import com.omair.service.CommentService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RestController 
@RequiredArgsConstructor
@RequestMapping("/posts")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @PostMapping("{id}/comments")
    public ResponseEntity<?> createComm(@PathVariable Long id, @RequestBody CommentDto comment){
        return new ResponseEntity<>(this.commentService.createComment(id, comment), HttpStatus.CREATED);
    }
    @GetMapping("/comments")
    public List<CommentDto> AllCom(){
     return this.commentService.getAllComment();
    }
    
    @DeleteMapping("comments/{id}")
    public ResponseEntity<?> deleteComm(@PathVariable Long id){
       return new ResponseEntity<>(commentService.deleteComment(id), HttpStatus.OK);
    }
    @GetMapping("comments/{id}")
    public ResponseEntity<?> getByid(@PathVariable long id){
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.FOUND);
    }
}
