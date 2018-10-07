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
import xyz.miroslaw.languide.model.Translation;
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

    @PostMapping("/user/{userName}/article")
    public String pair(@PathVariable String userName, @ModelAttribute @Valid ArticleCommand articleCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "index";
        }
        Article article = ConverterUtil.convertToArticle(articleCommand);
        article = articleService.createOrUpdateArticle(article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        model.addAttribute("article", article);
        return "article/pair";
    }
    @PreAuthorize("#userName == authentication.name")
    @PostMapping("/user/{userName}/article/{articleId}/updatedescription")
    public String updateArticleDescription(@PathVariable String userName, @Valid @ModelAttribute Article article, BindingResult bindingResult, @PathVariable long articleId) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "article/pair";
        }
        articleService.updateArticle(article, articleId);
        return "redirect:/user/" + userName + "/article/" + articleId;
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
        model.addAttribute("article", article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        return "article/articleform";
    }

    @GetMapping("/user/{userName}/article/{articleId}")
    public String showArticle(@PathVariable String userName, @PathVariable("articleId") long articleId, Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        model.addAttribute("translation", new Translation());
        return "article/view";
    }

    @GetMapping("/articles/all")
    public String showAllArticles(Model model) {
        model.addAttribute("articles", articleService.findAllPublicArticles());
        return "article/allarticles";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/articles")
    public String showUserArticles(@PathVariable String userName, @RequestParam("id") long id, Model model) {
        if (id == 0) {
            userService.getLoggedUser().ifPresent(e -> {
                model.addAttribute("articles", articleService.findArticlesByUserId(e.getId()));
                model.addAttribute("userId", e.getId());
            });
        } else {
            model.addAttribute("articles", articleService.findAllArticlesByNotebookId(id));
        }
        return "article/userarticles";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/article/{articleId}/delete")
    public String deleteById(@PathVariable String userName, @PathVariable long articleId, @RequestParam("id") long notebookId) {
        articleService.deleteById(articleId);
        //todo swap
        if (notebookId == 0) {
            return "redirect:/user/" + userName + "/articles?id=0";
        }
        return "redirect:/user/" + userName + "/articles?id=" + notebookId;
    }
}
