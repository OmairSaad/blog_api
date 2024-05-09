package com.omair.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Posts {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long postId;
    private String postTitle;
    private String postContent;
    private String imageName;
    private Date addDate; 
     
    @ManyToOne
    @JoinColumn(name = "CategId")
    private Category categId;
    
    @ManyToOne  
    @JoinColumn(name = "UserId")
    private User userId;
     
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}
