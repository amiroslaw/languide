package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.service.ArticleService;

import javax.validation.Valid;

@Slf4j
@Controller
public class HomeController {

    private ArticleService articleService;

    @Autowired
    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping({"/", ""})
    public String getHomePage() {
        return "index";
    }

    @PostMapping({"/", ""})
    @ResponseStatus(HttpStatus.CREATED)
//    public String pair(@Valid @ModelAttribute Article article, Model model, BindingResult bindingResult){
    public String pair(@Valid @ModelAttribute("article") ArticleCommand articleCommand, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            System.out.println("valid blad");
            return "index";
        }
        //TODO convert and after that result pass to the methods
        model.addAttribute("article", articleCommand);
        articleService.createArticle(articleCommand);
        return "article";
    }

    @GetMapping("/articles")
    public String getArticles() {

        return articleService.findArticles().toString();
    }

}
