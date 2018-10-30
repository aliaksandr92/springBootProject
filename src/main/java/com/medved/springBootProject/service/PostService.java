package com.medved.springBootProject.service;

import com.medved.springBootProject.model.Post;

import java.util.List;

public interface PostService
{
    void createPost(Post post);
    void deletePost(Long id);
    Post getPostById(Long id);
    List<Post> getPostByFromUserId(Long id);
    List<Post> getAllPosts();

    List<Post> getPostByToUserId(Long id);
}
