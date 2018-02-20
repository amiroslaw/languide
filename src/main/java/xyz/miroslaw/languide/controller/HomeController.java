package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.miroslaw.languide.command.ArticleCommand;

@Slf4j
@Controller
public class HomeController {



    @GetMapping({"/", ""})
    public String getHomePage(Model model) {
        ArticleCommand articleCommand = new ArticleCommand();
        model.addAttribute("articleCommand", articleCommand);
        return "index";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "/user/login";
    }
}
