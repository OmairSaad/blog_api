package com.omair.SecurityAuth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class jwtauthFilter extends OncePerRequestFilter{

    @Autowired
    private jwtHelper helper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String reqHeader = request.getHeader("Authorization");
        String token=null;
        String user = null;

        if(reqHeader!=null && reqHeader.startsWith("Bearer")){
            token = reqHeader.substring(7);
            try{
                user = helper.getUsername(token);
            }catch(IllegalArgumentException e){
                System.out.println("Illegal Args");
            }catch(ExpiredJwtException e){
                System.out.println("Token Expired");
            }
        }else{
            System.out.println("Invalid header value");
        }

        if(user!=null && SecurityContextHolder.getContext().getAuthentication()==null){
           
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(user);
            Boolean validateToken = helper.validateToken(token, userDetails);

            if(validateToken){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                System.out.println("Validation failed");
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
