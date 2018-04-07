package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.miroslaw.languide.service.DictionaryService;


@Slf4j
@Controller
public class DictionaryController {

    private DictionaryService dictionaryService;
    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/dictionary")
    public String showTranslations(Model model) {
        model.addAttribute("translations", dictionaryService.findUserTranslations());
        return "dictionary/alltranslations";
    }
}
