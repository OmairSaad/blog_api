package com.omair.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omair.Models.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    
}
