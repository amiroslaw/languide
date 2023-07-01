package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.service.ArticleService;
import xyz.miroslaw.languide.service.UserService;
import xyz.miroslaw.languide.util.ConverterUtil;

import javax.validation.Valid;

@Slf4j
@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private static final String ARTICLE = "article";
    private static final String ARTICLES = "articles";
    private static final String REDIRECT_USER = "redirect:/user/";


    @Autowired
    public ArticleController(ArticleService articleService, UserService userService) {
        this.articleService = articleService;
        this.userService = userService;
    }

    @ModelAttribute("articleCommand")
    public ArticleCommand articleCommandModel() {
        return new ArticleCommand();
    }

    @PostMapping("/user/{userName}/article")
    public String pair(@PathVariable String userName, @ModelAttribute @Valid ArticleCommand articleCommand,  Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "index";
        }
        Article article = ConverterUtil.convertToArticle(articleCommand);
        article = articleService.createArticle(article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        model.addAttribute(ARTICLE, article);
        return "article/pair";
    }
    @PreAuthorize("#userName == authentication.name")
    @PostMapping("/user/{userName}/article/{articleId}/updatedescription")
    public String updateArticleDescription(@PathVariable String userName, @Valid @ModelAttribute Article article, @PathVariable long articleId, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "article/pair";
        }
        articleService.updateArticle(article, articleId);
        return REDIRECT_USER + userName + "/article/" + articleId;
    }

    @PreAuthorize("#userName == authentication.name")
    @PostMapping("/user/{userName}/article/{articleId}/update")
    public String updateArticle(@PathVariable String userName, @RequestBody Article article, @PathVariable long articleId, @RequestParam("notebook") long notebookId) {
        articleService.updateArticle(article, articleId, notebookId);
        //todo redirect to /ariticles?id=0
        return "index";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/article/{articleId}/edit")
    public String showArticleForm(@PathVariable String userName, @PathVariable long articleId, Model model) {
        Article article = articleService.findById(articleId);
        model.addAttribute(ARTICLE, article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        return "article/articleform";
    }

    @GetMapping("/user/{userName}/article/{articleId}")
    public String showArticle(@PathVariable String userName, @PathVariable("articleId") long articleId, Model model) {
        model.addAttribute(ARTICLE, articleService.findById(articleId));
        model.addAttribute("translation", new Translation());
        return "article/view";
    }

    @GetMapping("/articles/all")
    public String showAllArticles(Model model) {
        model.addAttribute(ARTICLES, articleService.findAllPublicArticles());
        return "article/allarticles";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/articles")
    public String showUserArticles(@PathVariable String userName, @RequestParam("id") long id, Model model) {
        if (id == 0) {
            userService.getLoggedUser().ifPresent(e -> {
                model.addAttribute(ARTICLES, articleService.findArticlesByUserId(e.getId()));
                model.addAttribute("userId", e.getId());
            });
        } else {
            model.addAttribute(ARTICLES, articleService.findAllArticlesByNotebookId(id));
        }
        return "article/userarticles";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/article/{articleId}/delete")
    public String deleteById(@PathVariable String userName, @PathVariable long articleId, @RequestParam("id") long notebookId) {
        articleService.deleteById(articleId);
        if (notebookId == 0) {
            return REDIRECT_USER + userName + "/articles?id=0";
        }
        return REDIRECT_USER + userName + "/articles?id=" + notebookId;
    }
}
