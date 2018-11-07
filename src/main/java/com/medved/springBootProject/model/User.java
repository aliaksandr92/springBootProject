package com.medved.springBootProject.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "users")
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "login")
    private String login;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "gender")
    private String gender;

    @Column(name = "picture")
    private String picture;

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<User> friend = new HashSet<>();

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<User> wait = new HashSet<>();

    private User()
    {
    }

    public User(String firstName, String lastName, String gender, String login, String password)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.login = login;
        this.password = password;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getPicture()
    {
        return picture;
    }

    public void setPicture(String picture)
    {
        this.picture = picture;
    }

    public Set<Post> getPosts()
    {
        return posts;
    }

    public void setPosts(Set<Post> posts)
    {
        this.posts = posts;
    }

    public Set<User> getFriend()
    {
        return friend;
    }

    public void setFriend(Set<User> friend)
    {
        this.friend = friend;
    }

    public Set<User> getWait()
    {
        return wait;
    }

    public void setWait(Set<User> wait)
    {
        this.wait = wait;
    }
}
