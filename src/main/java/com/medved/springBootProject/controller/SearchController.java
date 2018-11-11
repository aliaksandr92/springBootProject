package com.medved.springBootProject.controller;

import com.medved.springBootProject.model.User;
import com.medved.springBootProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SearchController
{
    @Autowired
    public UserServiceImpl userService;

    @RequestMapping(value = "/searchPeoples", method = RequestMethod.GET)
    public String searchPage(Model model, Authentication auth)
    {
        User mainUser = userService.findByLogin(auth.getName());
        model.addAttribute("myId", mainUser.getId());

        List<User> peoples = userService.getAllUsers();
        peoples.remove(mainUser);
        model.addAttribute("users", peoples);

        model.addAttribute("count", mainUser.getInputRequest().size());

        return "search";
    }
}
