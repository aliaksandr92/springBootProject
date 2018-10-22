package com.medved.springBootProject.model;

import com.medved.springBootProject.model.enums.ResultFriendRequest;

import javax.persistence.*;

@Entity
@Table(name = "Friends")
public class Friend
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User fromUser;

    @Column(name = "to_user_id")
    private Long toUserId;

    @Column(name = "result_friend_request")
    @Enumerated(EnumType.STRING)
    private ResultFriendRequest friendRequest;

    public Friend()
    {
    }

    public Friend(User fromUser, Long toUserId, ResultFriendRequest friendRequest)
    {
        this.fromUser = fromUser;
        this.toUserId = toUserId;
        this.friendRequest = friendRequest;

    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public User getFromUserId()
    {
        return fromUser;
    }

    public void setFromUserId(User fromUser)
    {
        this.fromUser = fromUser;
    }

    public Long getToUserId()
    {
        return toUserId;
    }

    public void setToUserId(Long toUserId)
    {
        this.toUserId = toUserId;
    }

    public ResultFriendRequest isFriendRequest()
    {
        return friendRequest;
    }

    public void setResultFriendRequest(ResultFriendRequest friendRequest)
    {
        this.friendRequest = friendRequest;
    }
}
