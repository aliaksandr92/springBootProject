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
public class FriendController
{
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/id{userId}/addToFriends", method = RequestMethod.GET)
    public String addToFriends(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User mainUser = userService.findByLogin(auth.getName());
        User secondUser = userService.findUserById(userId);

        if (!mainUser.getInputRequest().contains(secondUser) && !mainUser.getFriends().contains(secondUser)
                && !mainUser.getOutputRequest().contains(secondUser)) {
            mainUser.getOutputRequest().add(secondUser);
            secondUser.getInputRequest().add(mainUser);
            userService.createUser(mainUser);
            userService.createUser(secondUser);
        }

        return "redirect:/id" + userId;
    }

    @RequestMapping(value = "/id{userId}/acceptFriendRequest", method = RequestMethod.GET)
    public String acceptFriendRequest(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User mainUser = userService.findByLogin(auth.getName());
        User secondUser = userService.findUserById(userId);

        mainUser.getInputRequest().remove(secondUser);
        mainUser.getFriends().add(secondUser);

        secondUser.getOutputRequest().remove(mainUser);
        secondUser.getFriends().add(mainUser);

        userService.createUser(mainUser);
        userService.createUser(secondUser);

        return "redirect:/id" + userId;
    }

    @RequestMapping(value = "/id{userId}/declineFriendRequest", method = RequestMethod.GET)
    public String declineFriendRequest(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User mainUser = userService.findByLogin(auth.getName());
        User secondUser = userService.findUserById(userId);

        mainUser.getInputRequest().remove(secondUser);
        secondUser.getOutputRequest().remove(mainUser);

        userService.createUser(mainUser);
        userService.createUser(secondUser);

        return "redirect:/id" + userId;
    }

    @RequestMapping(value = "/id{userId}/deleteFromFriends", method = RequestMethod.GET)
    public String deleteFromFriends(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User mainUser = userService.findByLogin(auth.getName());
        User secondUser = userService.findUserById(userId);

        if (mainUser.getFriends().contains(secondUser) && secondUser.getFriends().contains(mainUser)) {
            mainUser.getFriends().remove(secondUser);
            secondUser.getFriends().remove(mainUser);
            userService.createUser(mainUser);
            userService.createUser(secondUser);
        }

        return "redirect:/id" + userId;
    }

    @RequestMapping(value = "/id{userId}/friends", method = RequestMethod.GET)
    public String showListOfFriends(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User mainUser = userService.findByLogin(auth.getName());
        User secondUser = userService.findUserById(userId);

        model.addAttribute("myId", mainUser.getId());
        model.addAttribute("friends", secondUser.getFriends());

        model.addAttribute("count", mainUser.getInputRequest().size());

        return "listFriends";
    }

    @RequestMapping(value = "/id{userId}/requests", method = RequestMethod.GET)
    public String showInputRequests(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User mainUser = userService.findByLogin(auth.getName());
        User secondUser = userService.findUserById(userId);

        model.addAttribute("myId", mainUser.getId());
        model.addAttribute("requests", secondUser.getInputRequest());

        model.addAttribute("count", mainUser.getInputRequest().size());

        return "listRequests";
    }
}
