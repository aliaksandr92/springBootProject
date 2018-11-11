package com.medved.springBootProject.service.impl;

import com.medved.springBootProject.model.User;
import com.medved.springBootProject.repositories.UserRepository;
import com.medved.springBootProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(User user)
    {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id)
    {
        userRepository.deleteById(id);
    }

    @Override
    public User findByLogin(String login)
    {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User findUserById(Long id)
    {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}
