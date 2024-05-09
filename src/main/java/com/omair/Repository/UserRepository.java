package com.omair.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omair.Models.User;

public interface UserRepository extends JpaRepository<User, Long> {
   public User findByEmail(String email);
}
