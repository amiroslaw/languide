package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.repository.NotebookRepository;
import xyz.miroslaw.languide.service.NotebookService;
import xyz.miroslaw.languide.service.NotebookServiceImp;

import java.util.Set;

@Slf4j
@Controller
public class HomeController {
    
    @GetMapping({"/", "", "/index"})
    public String getHomePage(Model model) {
        ArticleCommand articleCommand = new ArticleCommand();
        System.out.println("test");
        model.addAttribute("articleCommand", articleCommand);
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/user/login";
    }



}
