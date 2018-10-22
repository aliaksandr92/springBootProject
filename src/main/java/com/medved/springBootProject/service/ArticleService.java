package com.medved.springBootProject.service;

import com.medved.springBootProject.model.Article;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService
{
    void createArticle(Article article);
    void deleteArticle(Long id);
    List<Article> getArticleByUserId(Long id);
    List<Article> getAllArticles();
}
