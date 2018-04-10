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
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.service.NotebookService;

import javax.validation.Valid;

@Slf4j
@Controller
public class NotebookController {

    private NotebookService notebookService;
    private static final String NOTEBOOK_NOTEBOOKFORM = "notebook/notebookform";

    @Autowired
    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/notebooks")
    public String showNotebooks(@PathVariable String userName, Model model) {
        model.addAttribute("notebooks", notebookService.findUserNotebooks());
        return "notebook/allnotebooks";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/notebook")
    public String showNotebookForm(@PathVariable String userName, Model model) {
        Notebook notebook = new Notebook();
        model.addAttribute("notebook", notebook);
        return NOTEBOOK_NOTEBOOKFORM;
    }

    @PreAuthorize("#userName == authentication.name")
    @PostMapping("/user/{userName}/notebook")
    public String saveOrUpdateNotebook(@PathVariable String userName, @Valid @ModelAttribute Notebook notebookAttribute, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.warn(objectError.toString()));
            return NOTEBOOK_NOTEBOOKFORM;
        }
        notebookService.createOrUpdateNotebook(notebookAttribute);
        return "redirect:/user/" + userName + "/notebooks";
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/notebook/{id}/update")
    public String fillNotebookForm(@PathVariable String userName, @PathVariable Long id, Model model) {
        model.addAttribute("notebook", notebookService.findById(id));
        return NOTEBOOK_NOTEBOOKFORM;
    }

    @PreAuthorize("#userName == authentication.name")
    @GetMapping("/user/{userName}/notebook/{id}/delete")
    public String deleteById(@PathVariable String userName, @PathVariable Long id) {
        notebookService.deleteById(userName, id);
        return "redirect:/user/" + userName + "/notebooks";

    }
}
