package com.omair.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.omair.Models.User;
import com.omair.Payloads.UserDTO;
import com.omair.Repository.UserRepository;

@Service
public class customeUserDetailservice implements UserDetailsService {
    @Autowired
    private ModelMapper mapper;
    
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return mapper.map(user, UserDTO.class);
    }
    
}
