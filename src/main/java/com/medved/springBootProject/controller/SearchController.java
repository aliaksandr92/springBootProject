package com.medved.springBootProject.controller;

import com.medved.springBootProject.model.User;
import com.medved.springBootProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SearchController
{
    @Autowired
    public UserServiceImpl userService;

    @RequestMapping(value = "/id{id}/searchPeoples", method = RequestMethod.GET)
    public String searchPage(Model model, Authentication auth, @PathVariable("id") Long id)
    {
        User main = userService.findByLogin(auth.getName());
        model.addAttribute("myId", main.getId());



        return "search";
    }
}
