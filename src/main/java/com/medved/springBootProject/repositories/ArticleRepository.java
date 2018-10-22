package com.medved.springBootProject.repositories;

import com.medved.springBootProject.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>
{
    Article findArticleByArticleText(String text);

    List<Article> findArticlesByUserId(Long id);
}
