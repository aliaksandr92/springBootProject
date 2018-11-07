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
        User user = userService.findByLogin(auth.getName());
        User fr = userService.findUserById(userId);

        /*if (friendService.getFriendByFromUserAndToUser(user, userId) == null) {
            Friend friend = new Friend(user, userId, ResultFriendRequest.WAITING);
            user.getFriends().add(friend);
            friendService.createFriend(friend);
        }*/

        if (!user.getWait().contains(fr) && !user.getFriend().contains(fr)) {
            user.getWait().add(fr);
            userService.createUser(user);
        }

        return "redirect:/id" + userId;
    }

    @RequestMapping(value = "/id{userId}/deleteFromFriends", method = RequestMethod.GET)
    public String deleteFromFriends(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User user = userService.findByLogin(auth.getName());
        User friend = userService.findUserById(userId);

        /*if (friendService.getFriendByFromUserAndToUser(user, userId) != null) {
            Friend friend = new Friend(user, userId, ResultFriendRequest.WAITING);
            user.getFriends().remove(friend);
            friendService.deleteFriend(friend);
        }*/

        if (user.getFriend().contains(friend)) {
            user.getFriend().remove(friend);
            userService.createUser(user);
        }

        return "redirect:/id" + userId;
    }

    @RequestMapping(value = "/id{userId}/acceptFriendRequest{friendId}", method = RequestMethod.GET)
    public String acceptFriendRequest(Model model, Authentication auth, @PathVariable("userId") Long userId)
    {
        User user = userService.findByLogin(auth.getName());
        User friend = userService.findUserById(userId);

        /*if (user.getFriend().contains(friend)) {
            user.getFriend().remove(friend);
            userService.createUser(user);
        }*/

        user.getWait().remove(friend);
        user.getFriend().add(friend);
        friend.getFriend().add(user);

        userService.createUser(user);
        userService.createUser(friend);

        return "redirect:/id" + userId;
    }

}
