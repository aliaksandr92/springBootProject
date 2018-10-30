package com.medved.springBootProject.controller;

import com.medved.springBootProject.model.Post;
import com.medved.springBootProject.model.User;
import com.medved.springBootProject.service.impl.PostServiceImpl;
import com.medved.springBootProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController implements ApplicationListener<ContextRefreshedEvent>
{

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
        initData();
    }

    private void initData()
    {
        User user = new User("Alex", "Medvedev", "Male", "user", "pass");
        User user1 = new User("Lex", "Med", "Male", "user1", "pass");
        User user2 = new User("BEata", "LIUBART", "Female", "user2", "pass");
        User user3 = new User("SERGEY", "Petrovich", "Male", "user3", "pass");

        userService.createUser(user);
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        Post post = new Post("AAAA", "BBBB");
        Post post1 = new Post("BEATA", "LIUBART");

        user1.getPosts().add(post);
        user1.getPosts().add(post1);

        post.setUser(user1);
        post1.setUser(user1);

        postService.createPost(post);
        postService.createPost(post1);


    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String indexPage(Model model, Authentication authentication)
    {
        if (authentication != null) {
            String login = authentication.getName();

            User user = userService.findByLogin(login);

            if (user != null) {
                model.addAttribute("user", user);
            }
            return "redirect:/userPage";
        }
        return "index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model)
    {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model)
    {
        return "registerPage";
    }

    @RequestMapping(value = "/createNewUser", method = RequestMethod.POST)
    public String createUser(Model model, @RequestParam("first_name") String firstName, @RequestParam("last_name") String lastName,
                             @RequestParam("gender") String gender, @RequestParam("username") String login,
                             @RequestParam("password") String pass)
    {
        if (userService.findByLogin(login) == null) {
            User newUser = new User(firstName, lastName, gender, login, pass);
            userService.createUser(newUser);

            return "redirect:/";
        }
        return "redirect:/register";
    }


    @RequestMapping(value = "/id{id}/addPost", method = RequestMethod.POST)
    public String addNewPost(Model model, Authentication authentication, @PathVariable("id") Long id,
                             @RequestParam("userPost") String userPost)
    {
        User fromUser = userService.findByLogin(authentication.getName());
        User toUser = userService.findUserById(id);

        if (userPost != null) {
            Post post = new Post(userPost, id);

            fromUser.getPosts().add(post);
            post.setUser(fromUser);

            postService.createPost(post);
        }

        return "redirect:/id" + id;
    }


    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public String userPage(Model model, Authentication authentication)
    {
        User user = userService.findByLogin(authentication.getName());

        return "redirect:/id" + user.getId();
    }

    @RequestMapping(value = "/id{id}", method = RequestMethod.GET)
    public String userInfo(Model model, Authentication authentication, @PathVariable("id") Long id)
    {
        User main = userService.findByLogin(authentication.getName());
        User second = userService.findUserById(id);

        boolean ownPost = false;

        if (main.getId() == id) ownPost = true;

        model.addAttribute("myId", main.getId());
        model.addAttribute("user", second);

        List<Post> userPosts = postService.getPostByToUserId(id);
        model.addAttribute("posts", userPosts);

        model.addAttribute("ownPage", ownPost);

        return "userInfo";
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
