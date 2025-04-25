package com.farhan.Socio.repository;

import com.farhan.Socio.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
