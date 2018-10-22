package com.medved.springBootProject.repositories;

import com.medved.springBootProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findUserByLogin(String login);
}
