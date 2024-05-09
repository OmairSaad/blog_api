package com.omair.Payloads;

import java.util.Date;

import com.omair.Models.Category;
import com.omair.Models.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    
    private Long postId;
    @NotBlank(message = "Can't blank")
    private String postTitle;
    @NotBlank(message = "Can't blank")
    private String postContent;
    @NotBlank(message = "Can't blank")
    private String imageName;
    private Date addDate; 
     

    private CategDto categId;
    
 
    private UserDTO userId;
}
