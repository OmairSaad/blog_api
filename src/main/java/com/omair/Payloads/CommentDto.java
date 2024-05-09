package com.omair.Payloads;

import com.omair.Models.Posts;
import com.omair.Models.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@NoArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private PostDto post;
    private UserDTO user;
}
