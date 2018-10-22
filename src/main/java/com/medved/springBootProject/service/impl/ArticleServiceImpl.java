package com.medved.springBootProject.service.impl;

import com.medved.springBootProject.model.Article;
import com.medved.springBootProject.repositories.ArticleRepository;
import com.medved.springBootProject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService
{
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void createArticle(Article article)
    {
        articleRepository.save(article);
    }

    @Override
    public void deleteArticle(Long id)
    {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> getArticleByUserId(Long id)
    {
        return articleRepository.findArticlesByUserId(id);
    }

    @Override
    public List<Article> getAllArticles()
    {
        return articleRepository.findAll();
    }
}
