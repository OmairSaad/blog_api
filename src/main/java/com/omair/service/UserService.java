package com.omair.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.omair.Models.User;
import com.omair.Payloads.Apiresponse;
import com.omair.Payloads.UserDTO;
import com.omair.Repository.UserRepository;
import com.omair.exception.ResourceNotFoundException;


import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRep;  
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    // @Autowired 
    // public UserService(UserRepository userRep){
    //     this.userRep = userRep;
    // }
    public List<UserDTO> getAllUsers(){
        List<User> users = userRep.findAll();
        return users.stream().map(user->userToDto(user)).collect(Collectors.toList());
    }

    public UserDTO creatUser( @RequestBody UserDTO data){
          data.setPassword(passwordEncoder.encode(data.getPassword()));
          User user = userRep.save(DtoToUser(data));
          return userToDto(user);
    }

    public UserDTO getUser(@PathVariable Long id){
     User user = userRep.findById(id)
     .orElseThrow(()-> new ResourceNotFoundException("Id not found: "+id));
     return userToDto(user);
    }

    public ResponseEntity<Apiresponse> delUser(@PathVariable Long id){
        User user = userRep.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Id not found: "+id));
        userRep.delete(user);
        return new ResponseEntity<Apiresponse>(new Apiresponse("User Deleted", true), HttpStatus.OK);
    }

    public UserDTO updateUser(@RequestBody UserDTO data,@PathVariable Long id){
        User user = userRep.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Id not found: "+id));
        user.setName(data.getName());
        user.setAbout(data.getAbout());
        user.setEmail(data.getEmail());
        user.setPassword(data.getPassword());
        userRep.save(user);
        return userToDto(user);
    }


    public User DtoToUser(UserDTO user){
        User us = new User();
        us.setName(user.getName());
        us.setAbout(user.getAbout());
        us.setEmail(user.getEmail());
        us.setPassword(user.getPassword());
        return us;
    }
    public UserDTO userToDto (User user){
        UserDTO us = new UserDTO();
        us.setId(user.getId());
        us.setName(user.getName());
        us.setAbout(user.getAbout());
        us.setEmail(user.getEmail());
        us.setPassword(user.getPassword());
        return us;
    }
}
