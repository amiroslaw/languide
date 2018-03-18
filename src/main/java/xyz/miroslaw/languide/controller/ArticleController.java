package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.service.ArticleService;
import xyz.miroslaw.languide.service.UserService;
import xyz.miroslaw.languide.util.ConverterUtil;

import javax.validation.Valid;

@Slf4j
@Controller
public class ArticleController {
    private ArticleService articleService;
    private UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @ModelAttribute("articleCommand")
    public ArticleCommand articleCommandModel() {
        return new ArticleCommand();
    }

    @PostMapping("/article")
    @ResponseStatus(HttpStatus.CREATED)
    public String pair(@ModelAttribute @Valid ArticleCommand articleCommand, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //todo never true maybe I should delete
            log.error("with error");
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "/index";
        }
        boolean areArticleFieldsEmpty = articleCommand.getFirstLanguage().isEmpty() || articleCommand.getSecondLanguage().isEmpty();
        if (areArticleFieldsEmpty) {
            bindingResult.rejectValue("firstLanguage", null, "Please fill text");
            return "/index";
        }
        Article article = ConverterUtil.convertToArticle(articleCommand);
        article = articleService.createArticle(article);

        model.addAttribute("notebooks", userService.getUserNotebooks());
        model.addAttribute("article", article);
        return "/article/pair";
    }

    @PostMapping("/articleform/{articleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveArticle(@ModelAttribute Article article, @PathVariable long articleId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "/articleform";
        }
        articleService.updateArticleDescription(article, articleId);
        return "/index";
    }

    @GetMapping("/articleform/{articleId}")
    public String showArticleForm(@PathVariable long articleId, Model model) {
        log.warn("article id" + articleId);
        Article article = articleService.findById(articleId);
        model.addAttribute("article", article);
        return "/article/articleform";
    }

    @GetMapping("/all_articles")
    public String showAllArticles(Model model) {
        model.addAttribute("articles", articleService.findArticles());
        return "article/allarticles";
    }

    @GetMapping("/user/{userId}/article/{articleId}")
    public String showArticle(@PathVariable("userId") long userId, @PathVariable("articleId") long articleId, Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        return "article/view";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user_articles")
    public String showUserArticles(Model model) {
        userService.getLoggedUser().ifPresent(e -> {
            model.addAttribute("articles", articleService.findArticlesByUserId(e.getId()));
            model.addAttribute("userId", e);
        });
        return "article/userarticles";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
