package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.miroslaw.languide.service.DictionaryService;


@Slf4j
@Controller
public class DictionaryController {

    private DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/dictionary")
    public String showTranslations(@PathVariable String userName, Model model) {
        model.addAttribute("translations", dictionaryService.findUserTranslations());
        return "dictionary/alltranslations";
    }
}
