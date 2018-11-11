package com.medved.springBootProject.controller;

import com.medved.springBootProject.model.User;
import com.medved.springBootProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditUserInfoController
{
    @Autowired
    public UserServiceImpl userService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditUserInfoPage(Model model, Authentication auth)
    {
        User mainUser = userService.findByLogin(auth.getName());

        model.addAttribute("mainUser", mainUser);

        model.addAttribute("count", mainUser.getInputRequest().size());

        return "editUserInfo";
    }

    @RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
    public String editUserInfo(Model model, Authentication auth, @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName)
    {
        User mainUser = userService.findByLogin(auth.getName());

        mainUser.setFirstName(firstName);
        mainUser.setLastName(lastName);
        userService.createUser(mainUser);

        return "redirect:/id" + mainUser.getId();
    }
}
