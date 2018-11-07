package com.medved.springBootProject.model;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_name")
    private String postName;

    @Column(name = "post_text")
    private String postText;

    @ManyToOne
    private User fromUserId;

    private Long toUserId;

    private Post()
    {
    }

    public Post(User fromUserId, String postText, Long toUserId)
    {
        this.fromUserId = fromUserId;
        this.postText = postText;
        this.toUserId = toUserId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPostName()
    {
        return postName;
    }

    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostText()
    {
        return postText;
    }

    public void setPostText(String postText)
    {
        this.postText = postText;
    }

    public User getUser()
    {
        return fromUserId;
    }

    public void setUser(User fromUserId)
    {
        this.fromUserId = fromUserId;
    }

    public Long getToUserId()
    {
        return toUserId;
    }

    public void setToUserId(Long toUserId)
    {
        this.toUserId = toUserId;
    }
}
