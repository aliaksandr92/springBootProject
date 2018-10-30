package com.medved.springBootProject.service;

import com.medved.springBootProject.model.User;

public interface UserService
{
    void createUser(User user);
    void deleteUser(Long id);
    User findByLogin(String login);
    User findUserById(Long id);
}
