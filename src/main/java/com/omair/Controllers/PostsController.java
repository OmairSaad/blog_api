package com.omair.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.omair.Models.Posts;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.PostDto;
import com.omair.Payloads.PostResponse;
import com.omair.config.AppConstrants;
import com.omair.service.PostsService;
import com.omair.service.fileService;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequiredArgsConstructor
@Getter
@Setter
@RequestMapping("/")
public class PostsController {
    @Autowired
    private PostsService ps;
    
    @Autowired
    private fileService fs;

    @PostMapping("/user/{userId}/categ/{categId}/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto data, @PathVariable Long userId, @PathVariable Long categId){
        return ps.crePost(data, userId, categId);
    } 
    
    @GetMapping("/posts")  // posts?pageNumber=0&pageSize=5&sortBy=postTitle  uri /posts only => 0 page, and 5 index
    public PostResponse getAllPosts(
        
        @RequestParam(value = "pageNumber", defaultValue = AppConstrants.PAGE_NUMBER, required = false)Integer pageNumber,
        @RequestParam(value = "pageSize", defaultValue = AppConstrants.PAGE_SIZE, required = false) Integer pageSize,
        @RequestParam(value = "sortBy", defaultValue = AppConstrants.SORT_BY, required = false) String sortBy,
        @RequestParam(value = "sortDir", defaultValue = AppConstrants.SORT_DIR, required = false) String sortDir
        ){
        return ps.getAllPosts(pageNumber, pageSize, sortBy, sortDir);  //sortDir Asscending/Descending order
    }

    @GetMapping("/posts/{id}")
    public PostDto getByid(@PathVariable Long id){
        return ps.postById(id);
    }

    @GetMapping("/user/{id}/posts")
    public ResponseEntity<List<PostDto>> getByUsr(@PathVariable Long id){
        return ps.postByUser(id);
    }

    @GetMapping("/categ/{id}/posts")
    public List<PostDto> getByCat(@PathVariable Long id){
        return ps.postByCateg(id);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Apiresponse> delPost(@PathVariable Long id){
        return ps.delPost(id);
    }

    @PutMapping("/posts/{id}")
    public PostDto updPost(@Valid @RequestBody PostDto data, @PathVariable Long id){
      return ps.updatePost(data, id);
    }

    //Search
    @GetMapping("/posts/search/{key}")
    public List<PostDto> search(@PathVariable String key){
     return ps.searchByTitle(key);
    }
    
     
    @PostMapping("/upload/image/{id}")
    public PostDto file(@RequestParam("image") MultipartFile image, @PathVariable Long id) throws IOException{
        PostDto post = ps.postById(id);
       String file=  fs.uploadFile(image);
       post.setImageName(file);
        return ps.updatePost(post, id);
    } 

    //get image localhost:8080/images/imageName
   
}
