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
import xyz.miroslaw.languide.service.ArticleService;

import javax.validation.Valid;

@Slf4j
@Controller
public class ArticleController {
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ModelAttribute("articleCommand")
    public ArticleCommand articleCommandModel() {
        return new ArticleCommand();
    }

    @PostMapping("/user/{userId}/article")
    @ResponseStatus(HttpStatus.CREATED)
    public String pair(@ModelAttribute @Valid ArticleCommand articleCommand, Model model, BindingResult bindingResult, @PathVariable Long userId) {
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
        //todo add parameter and direct to view or articleform
        // convert but don't save
        Article article = articleService.createArticle(articleCommand);
        model.addAttribute("article", article);
        return "/article/view";
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
