package com.omair.Payloads;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UserDTO implements UserDetails{
    private Long id;
    
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mendatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    // @Size(min=10, message = "Must Use 10 char")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", 
             message = "Password must be at least 8 characters long and contain at least one digit, one lowercase letter, one uppercase letter, one special character, and no whitespace.")
    private String password;
    @NotBlank(message = "About is mandatory")
    private String about;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
   }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;

    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
