package com.henrique.springsecurity.repositories;

import com.henrique.springsecurity.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
