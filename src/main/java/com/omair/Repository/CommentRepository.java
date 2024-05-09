package com.omair.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omair.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
