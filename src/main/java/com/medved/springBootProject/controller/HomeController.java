package com.medved.springBootProject.controller;

import com.medved.springBootProject.model.Article;
import com.medved.springBootProject.model.User;
import com.medved.springBootProject.model.enums.Role;
import com.medved.springBootProject.service.impl.ArticleServiceImpl;
import com.medved.springBootProject.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController implements ApplicationListener<ContextRefreshedEvent>
{

    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)
    {
        initData();
    }

    private void initData()
    {
        User user1 = new User("Alex", "Medvedev", "user", "pass", Role.ADMIN);
        userService.createUser(user1);

        Article article = new Article("AAAA", "BBBB");
        Article article1 = new Article("BEATA", "LIUBART");

        user1.getArticles().add(article);
        user1.getArticles().add(article1);

        article.setUser(user1);
        article1.setUser(user1);

        articleService.createArticle(article);
        articleService.createArticle(article1);


    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String indexPage(Model model, Authentication authentication)
    {
        if (authentication != null) {
            String login = authentication.getName();

            User user = userService.findByLogin(login);

            if (user != null) {
                model.addAttribute("user", user);
            }
            return "redirect:/userPage";
        }
        return "index";
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(Model model)
    {
        return "login";
    }

    @RequestMapping(value = "/delete/id{id}", method = RequestMethod.GET)
    public String deleteUserPage(Model model, @PathVariable("id") Long id)
    {
        articleService.deleteArticle(id);

        model.addAttribute("articles", articleService.getAllArticles());

        return "redirect:/userPage";
    }


    @RequestMapping(value = "/userPage", method = RequestMethod.GET)
    public String registerPage(Model model, Authentication authentication)
    {
        model.addAttribute("articles", articleService.getArticleByUserId((long) 1));
        return "userPage";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage(Model model)
    {
        return "redirect:/";
    }
}
