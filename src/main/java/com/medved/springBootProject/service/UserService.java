package com.medved.springBootProject.service;

import com.medved.springBootProject.model.User;

import java.util.List;

public interface UserService
{
    void createUser(User user);
    void deleteUser(Long id);
    User findByLogin(String login);
    User findUserById(Long id);
    List<User> getAllUsers();
}
