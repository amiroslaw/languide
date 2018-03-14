package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import xyz.miroslaw.languide.service.ArticleService;
import xyz.miroslaw.languide.service.NotebookService;
import xyz.miroslaw.languide.util.ConverterUtil;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.HashSet;

@Slf4j
@Controller
public class ArticleController {
    private ArticleService articleService;
    private NotebookService notebookService;

    @Autowired
    public ArticleController(ArticleService articleService, NotebookService notebookService) {
        this.articleService = articleService;
        this.notebookService = notebookService;
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
        if (areArticleFieldsEmpty){
            bindingResult.rejectValue("firstLanguage", null, "Please fill text");
            return "/index";
        }
        Article article = ConverterUtil.convertToArticle(articleCommand);
        article = articleService.createArticle(article);
        String userName = getLoggedUser();
        log.warn("user name " + userName);
        HashSet<Notebook> notebooks = (HashSet<Notebook>) notebookService.findUserNotebooks(userName);
        notebooks.forEach(e -> System.out.println(e.getTitle()));
        model.addAttribute("notebooks", notebooks);
        model.addAttribute("article", article);
        return "/article/view";
    }

    private String getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
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

    private Article attachFieldsToArticle(Article article, long articleId) {
        Article oldArticle = articleService.findById(articleId);
        oldArticle.setTitle(article.getTitle());
        oldArticle.setTag(article.getTag());
        oldArticle.setCreationDate(Calendar.getInstance().getTime());
        oldArticle.setNotebook(article.getNotebook());
        return oldArticle;
    }

    @GetMapping("/articleform/{articleId}")
    public String showArticleForm(@PathVariable long articleId, Model model) {
        log.warn("article id" + articleId);
        Article article = articleService.findById(articleId);
        model.addAttribute("article", article);
        return "/article/articleform";
    }


    @GetMapping("/articles")
    public String showArticles() {
        return "article";
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
