package com.medved.springBootProject.service.impl;

import com.medved.springBootProject.model.Post;
import com.medved.springBootProject.repositories.PostRepository;
import com.medved.springBootProject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService
{
    @Autowired
    private PostRepository postRepository;

    @Override
    public void createPost(Post post)
    {
        postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id)
    {
        return postRepository.findById(id).get();
    }

    @Override
    public void deletePost(Long id)
    {
        postRepository.deleteById(id);
    }

    @Override
    public List<Post> getPostByFromUserId(Long id)
    {
        return postRepository.findPostsByFromUserId(id);
    }

    @Override
    public List<Post> getAllPosts()
    {
        return postRepository.findAll();
    }

    @Override
    public List<Post> getPostByToUserId(Long id)
    {
        return postRepository.findPostsByToUserId(id);
    }
}
