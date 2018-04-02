package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String pair(@ModelAttribute @Valid ArticleCommand articleCommand, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //todo never true maybe I should delete
            log.error("with error");
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "/article/pair";
        }
        boolean areArticleFieldsEmpty = articleCommand.getFirstLanguage().isEmpty() || articleCommand.getSecondLanguage().isEmpty();
        if (areArticleFieldsEmpty) {
            bindingResult.rejectValue("firstLanguage", null, "Please fill text");
            return "/index";
        }
        Article article = ConverterUtil.convertToArticle(articleCommand);
        article = articleService.createOrUpdateArticle(article);
        model.addAttribute("notebooks", userService.getUserNotebooks());
        model.addAttribute("article", article);
        return "/article/pair";
    }

    @PostMapping("/articledescription/{articleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveArticle(@Valid @ModelAttribute Article article, @PathVariable long articleId, BindingResult bindingResult, RedirectAttributes redirectAttrs) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return "article/pair";
        }
        articleService.updateArticleDescription(article, articleId);
        return "index";
        //todo redirect
//        redirectAttrs.addAttribute("articleId", articleId).addFlashAttribute("message", "Account created!");
//        return "redirect:/article/" + articleId;
//        user_articles?id=0
    }
    @PostMapping("/article/{articleId}/update")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateArticle(@RequestBody Article article, @PathVariable long articleId, @RequestParam("notebook") long notebookId) {
        articleService.updateArticle(article, articleId, notebookId);
        return "index";
//        return new ModelAndView("index.html");
//        redirectAttrs.addAttribute("articleId", articleId).addFlashAttribute("message", "Account created!");
//        return "redirect:/article/" + articleId;
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

    @GetMapping("/all_articles")
    public String showAllArticles(Model model) {
            model.addAttribute("articles", articleService.findPublicArticles());
        return "article/allarticles";
    }

    @GetMapping("/user_articles")
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
        if(id == 0){
            return "redirect:/user_articles?id="+id;
        }
        return "redirect:/user_articles?id=0";
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
