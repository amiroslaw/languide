package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.service.ArticleService;
import xyz.miroslaw.languide.service.NotebookService;
import xyz.miroslaw.languide.service.UserService;
import xyz.miroslaw.languide.util.ConverterUtil;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
@Controller
public class ArticleController {
    private ArticleService articleService;
    private NotebookService notebookService;
    private UserService userService;

    @Autowired
    public ArticleController(ArticleService articleService, NotebookService notebookService, UserService userService) {
        this.articleService = articleService;
        this.notebookService = notebookService;
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
            //todo never true maybe delete
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

        model.addAttribute("notebooks", getUserNotebooks());
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
        Article oldArticle = attachFieldsToArticle(article, articleId);
        articleService.createArticle(oldArticle);
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
//        model.addAttribute("userId", getLoggedUser().ifPresent(this::get()));
        getLoggedUser().ifPresent(e -> model.addAttribute("userId", e.getId()));
        model.addAttribute("articles", articleService.findArticles());
        return "article/userarticles";
    }

    private HashSet<Notebook> getUserNotebooks() {
        if (getLoggedUser().isPresent()) {
            return (HashSet<Notebook>) notebookService.findUserNotebooks(getLoggedUser().get().getId());
        }
        return null;
    }

    private Optional<User> getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findByName(auth.getName());
    }

    private Article attachFieldsToArticle(Article article, long articleId) {
        Article oldArticle = articleService.findById(articleId);
        oldArticle.setTitle(article.getTitle());
        oldArticle.setTag(article.getTag());
        oldArticle.setCreationDate(Calendar.getInstance().getTime());
        oldArticle.setNotebook(article.getNotebook());
        return oldArticle;
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
