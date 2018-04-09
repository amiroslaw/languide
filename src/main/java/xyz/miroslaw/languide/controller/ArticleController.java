package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/article")
    @ResponseStatus(HttpStatus.CREATED)
    public String pair(@ModelAttribute @Valid ArticleCommand articleCommand, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "/index";
        }
        Article article = ConverterUtil.convertToArticle(articleCommand);
        article = articleService.createOrUpdateArticle(article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        model.addAttribute("article", article);
        return "/article/pair";
    }

    @PostMapping("/article/{articleId}/updatedescription")
    public String updateArticleDescription(@Valid @ModelAttribute Article article, BindingResult bindingResult, @PathVariable long articleId) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "/article/pair";
        }
        articleService.updateArticle(article, articleId);
        return "redirect:/article/" + articleId;
    }

    @PostMapping("/article/{articleId}/update")
    public String updateArticle(@RequestBody Article article, @PathVariable long articleId, @RequestParam("notebook") long notebookId) {
        articleService.updateArticle(article, articleId, notebookId);
        //todo redirect to /ariticles?id=0
        return "index";
    }

    @GetMapping("/article/{articleId}/edit")
    public String showArticleForm(@PathVariable long articleId, Model model) {
        Article article = articleService.findById(articleId);
        model.addAttribute("article", article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        return "/article/articleform";
    }

    @GetMapping("/article/{articleId}")
    public String showArticle(@PathVariable("articleId") long articleId, Model model) {
        model.addAttribute("article", articleService.findById(articleId));
        model.addAttribute("translation", new Translation());
        return "article/view";
    }

    @GetMapping("/articles/all")
    public String showAllArticles(Model model) {
        model.addAttribute("articles", articleService.findPublicArticles());
        return "article/allarticles";
    }

    @GetMapping("/articles")
    public String showUserArticles(@RequestParam("id") long id, Model model) {
        if (id == 0) {
            userService.getLoggedUser().ifPresent(e -> {
                model.addAttribute("articles", articleService.findArticlesByUserId(e.getId()));
                model.addAttribute("userId", e.getId());
            });
        } else {
            model.addAttribute("articles", articleService.findNotebookArticles(id));
        }
        return "article/userarticles";
    }

    @GetMapping("/article/{articleId}/delete")
    public String deleteById(@RequestParam("id") long id, @PathVariable long articleId) {
        articleService.deleteById(articleId);
        if (id == 0) {
            return "redirect:/articles?id=" + id;
        }
        return "redirect:/articles?id=0";
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
