package xyz.miroslaw.languide.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String NOTEBOOK_NOTEBOOKFORM = "notebook/notebookform";
    @Autowired
    private NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @GetMapping("/notebooks")
    public String showNotebooks(Model model) {
        model.addAttribute("notebooks", notebookService.findUserNotebooks());
        return "notebook/allnotebooks";
    }

    @GetMapping("/notebook")
    public String showNotebookForm(Model model) {
        Notebook notebook = new Notebook();
        model.addAttribute("notebook", notebook);
        return NOTEBOOK_NOTEBOOKFORM;
    }

    @PostMapping("/notebook")
    public String saveOrUpdateNotebook(@Valid @ModelAttribute Notebook notebookAttribute, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.warn(objectError.toString());
            });
            return NOTEBOOK_NOTEBOOKFORM;
        }
        notebookService.createOrUpdateNotebook(notebookAttribute);
        return "redirect:/notebooks";
    }

    @GetMapping("/notebook/{id}/update")
    public String fillNotebookForm(@PathVariable Long id, Model model) {
        model.addAttribute("notebook", notebookService.findById(id));
        return NOTEBOOK_NOTEBOOKFORM;
    }

    @GetMapping("notebook/{id}/delete")
    public String deleteById(@PathVariable Long id){
        notebookService.deleteById(id);
        return "redirect:/notebooks";
    }
}
