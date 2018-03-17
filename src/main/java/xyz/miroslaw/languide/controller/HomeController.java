package xyz.miroslaw.languide.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.miroslaw.languide.command.ArticleCommand;


@Controller
public class HomeController {
    
    @GetMapping({"/", "", "/index"})
    public String getHomePage(Model model) {
        ArticleCommand articleCommand = new ArticleCommand();
        model.addAttribute("articleCommand", articleCommand);
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/user/login";
    }



}
