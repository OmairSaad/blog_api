package com.omair.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.omair.SecurityAuth.jwtAuthEntryPoint;
import com.omair.SecurityAuth.jwtauthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    // Your security configuration code here

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private jwtAuthEntryPoint point;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private jwtauthFilter filter;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("posts/**").authenticated();
                    authorize.requestMatchers("login").permitAll();
                    authorize.requestMatchers("user/create").permitAll();
                    authorize.anyRequest().authenticated();
                }).exceptionHandling(ex-> ex.authenticationEntryPoint(point))
                  .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                  http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }
}