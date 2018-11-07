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
public class UserController implements ApplicationListener<ContextRefreshedEvent>
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
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String indexPage(Model model, Authentication auth)
    {
        if (auth != null) {
            String login = auth.getName();

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

    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public String userPage(Model model, Authentication auth)
    {
        User user = userService.findByLogin(auth.getName());

        return "redirect:/id" + user.getId();
    }

    @RequestMapping(value = "/id{id}", method = RequestMethod.GET)
    public String userInfo(Model model, Authentication auth, @PathVariable("id") Long id)
    {
        User main = userService.findByLogin(auth.getName());
        User second = userService.findUserById(id);

        boolean ownPost = false;

        if (main.getId() == id) ownPost = true;

        model.addAttribute("myId", main.getId());
        model.addAttribute("user", second);

        List<Post> userPosts = postService.getPostByToUserId(id);
        model.addAttribute("posts", userPosts);

        model.addAttribute("ownPost", ownPost);

        String link = "/id" + id;
        String action = "It's your page";


        if (main.getId() != id) {
            if (!main.getFriend().contains(second) && !main.getWait().contains(second)) {
                link = "/id" + id + "/addToFriends";
                action = "Add to friends";
            } else if (main.getWait().contains(second)) {
                link = "/id" + id;
                action = "Wait for answer";
            } else {
                link = "/id" + id + "/deleteFromFriends";
                action = "Delete from friends";
            }
        }


        model.addAttribute("link", link);
        model.addAttribute("action", action);
        return "userInfo";
    }
}