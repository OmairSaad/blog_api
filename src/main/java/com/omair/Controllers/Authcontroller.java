package com.omair.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.omair.Models.jwtReq;
import com.omair.Models.jwtRes;
import com.omair.Payloads.UserDTO;
import com.omair.SecurityAuth.jwtHelper;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@Getter
@Setter
@RequiredArgsConstructor
public class Authcontroller {

    @Autowired
    private AuthenticationManager manager;
    
    @Autowired
    private UserDetailsService us;

    @Autowired
    private jwtHelper helper;

    @PostMapping("/login")
    public ResponseEntity<?> send(@RequestBody jwtReq request){

        this.doAuthenticate(request.getUser(), request.getPassword());
        UserDetails userDetails = us.loadUserByUsername(request.getUser());
        System.out.println(userDetails);
        String token = this.helper.generateToken(userDetails);
        jwtRes reponse = new jwtRes();
        reponse.setToken(token);
        reponse.setUser(userDetails.getUsername());
        return new ResponseEntity<>(reponse, HttpStatus.OK);
    }
    
    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong Pssword");
        }
   }
}
