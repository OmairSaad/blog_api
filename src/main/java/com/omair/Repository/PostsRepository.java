package com.omair.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omair.Models.Category;
import com.omair.Models.Posts;
import com.omair.Models.User;

public interface PostsRepository extends JpaRepository<Posts, Long> {
   public List<Posts> findByUserId(User uid);

   public List<Posts>  findByCategId(Category cat);

   public List<Posts> findByPostTitleContaining(String search);
}
