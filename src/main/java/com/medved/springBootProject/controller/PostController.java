package com.medved.springBootProject.controller;

import com.medved.springBootProject.model.Post;
import com.medved.springBootProject.model.User;
import com.medved.springBootProject.service.impl.PostServiceImpl;
import com.medved.springBootProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController
{
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/id{id}/addPost", method = RequestMethod.POST)
    public String addNewPost(Model model, Authentication authentication, @PathVariable("id") Long id,
                             @RequestParam("userPost") String userPost)
    {
        User fromUser = userService.findByLogin(authentication.getName());

        if (userPost != null) {
            Post post = new Post(fromUser, userPost, id);

            fromUser.getPosts().add(post);

            postService.createPost(post);
        }

        return "redirect:/id" + id;
    }

    @RequestMapping(value = "/id{userId}/deletePost{postId}", method = RequestMethod.GET)
    public String deletePost(Model model, Authentication auth, @PathVariable("userId") Long userId,
                             @PathVariable("postId") Long postId)
    {
        Post post = postService.getPostById(postId);

        userService.findUserById(userId).getPosts().remove(post);
        postService.deletePost(postId);

        return "redirect:/id" + userId;
    }
}
