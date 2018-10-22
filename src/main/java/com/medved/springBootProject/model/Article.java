package com.medved.springBootProject.model;

import javax.persistence.*;

@Entity
@Table(name = "articles")
public class Article
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "article_name")
    private String articleName;

    @Column(name = "article_text")
    private String articleText;

    @ManyToOne
    private User user = new User();

    public Article()
    {
    }

    public Article(String articleName, String articleText)
    {
        this.articleName = articleName;
        this.articleText = articleText;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getArticleName()
    {
        return articleName;
    }

    public void setArticleName(String articleName)
    {
        this.articleName = articleName;
    }

    public String getArticleText()
    {
        return articleText;
    }

    public void setArticleText(String articleText)
    {
        this.articleText = articleText;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
