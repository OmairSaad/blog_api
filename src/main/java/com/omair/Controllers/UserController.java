package com.omair.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omair.Models.User;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.UserDTO;
import com.omair.service.UserService;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Validated
@RestController
@RequiredArgsConstructor
@Getter
@Setter
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    
    // @Autowired
    // public UserController(UserService userService){
    //     this.userService = userService;
    // }
    @GetMapping("/")
    public List<UserDTO> getAUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO data){
       UserDTO user = userService.creatUser(data);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id){
    return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Apiresponse> delUser(@PathVariable Long id){
        return userService.delUser(id);
       
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO data, @PathVariable Long id){
        UserDTO user = userService.updateUser(data,id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
