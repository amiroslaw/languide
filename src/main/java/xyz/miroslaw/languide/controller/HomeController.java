package xyz.miroslaw.languide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.service.ArticleServiceImp;

@Controller
public class HomeController {

    private ArticleServiceImp articleService;
    @Autowired
    public HomeController(ArticleServiceImp articleService){
        this.articleService = articleService;
    }

    @GetMapping({"/", ""})
    public String getHomePage(){
        return "index";
    }

    @PostMapping({"/", ""})
    @ResponseStatus(HttpStatus.CREATED)
    public String pair(Article article, Model model){
        return "article";
    }

    @GetMapping("/articles")
    public String getArticles(){
        return articleService.findArticles().toString();
    }

}
