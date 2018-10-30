package com.medved.springBootProject.repositories;

import com.medved.springBootProject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>
{
    List<Post> findPostsByFromUserId(Long id);

    List<Post> findPostsByToUserId(Long id);
}
