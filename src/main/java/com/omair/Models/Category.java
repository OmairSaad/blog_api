package com.omair.Models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter; 
  
@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="Categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500) 
    private String description;
    private String type;
    private String title;
    
    @OneToMany(mappedBy = "categId", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Posts> categPost = new ArrayList<>();

    
}
