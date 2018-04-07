package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.service.TranslationService;

import javax.validation.Valid;

@Slf4j
@Controller
public class TranslationController {

    private static final String TRANSLATION_FORM = "dictionary/translationform";
    private TranslationService translationService;
    @Autowired
    public TranslationController(TranslationService transactionService) {
        this.translationService = transactionService;
    }

    @PostMapping("/translation")
    public String saveOrUpdateTranslation(@Valid @ModelAttribute Translation translation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return TRANSLATION_FORM;
        }
        translationService.createOrUpdateTranslation(translation);
        return "redirect:/dictionary";
    }
    @PostMapping("/translation/{articleId}")
    public void saveTranslation(@Valid @ModelAttribute Translation translation, @PathVariable Long articleId) {
        translationService.createOrUpdateTranslation(translation);
//        return "redirect:/article/" + articleId;
    }

    @GetMapping("/translation")
    public String showNotebookForm(Model model) {
        Translation translation = new Translation();
        model.addAttribute("translation", translation);
        return TRANSLATION_FORM;
    }
    @GetMapping("/translation/{id}/update")
    public String fillTranslationForm(@PathVariable Long id, Model model) {
        model.addAttribute("translation", translationService.findById(id));
        return TRANSLATION_FORM;
    }

    @GetMapping("/translation/{id}/delete")
    public String deleteTranslationById(@PathVariable Long id) {
        translationService.deleteById(id);
        return "redirect:/dictionary";
    }
}
