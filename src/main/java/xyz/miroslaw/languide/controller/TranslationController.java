package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    @PreAuthorize("#userName == authentication.name")
    @PostMapping("/user/{userName}/translation")
    public String saveOrUpdateTranslation(@PathVariable String userName, @Valid @ModelAttribute Translation translation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return TRANSLATION_FORM;
        }
        translationService.createOrUpdateTranslation(translation);
        return "redirect:/user/" + userName + "/dictionary";
    }
    @PreAuthorize("#userName == authentication.name")
    @PostMapping("/user/{userName}/translation/{articleId}")
    public String saveTranslation(@PathVariable String userName, @Valid @ModelAttribute Translation translation, @PathVariable Long articleId) {
        translationService.createOrUpdateTranslation(translation);
        return "redirect:/user/" + userName + "/article/" + articleId;
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/translation")
    public String showNotebookForm(@PathVariable String userName, Model model) {
        Translation translation = new Translation();
        model.addAttribute("translation", translation);
        return TRANSLATION_FORM;
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/translation/{id}/update")
    public String fillTranslationForm(@PathVariable String userName, @PathVariable Long id, Model model) {
        model.addAttribute("translation", translationService.findById(id));
        return TRANSLATION_FORM;
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/translation/{id}/delete")
    public String deleteTranslationById(@PathVariable String userName, @PathVariable Long id) {
        translationService.deleteById(id);
        return "redirect:/user/" + userName + "/dictionary";
    }
}
