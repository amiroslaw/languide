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
public class DictionaryController {

    @Autowired
    private NotebookService notebookService;

    public DictionaryController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }



    //todo multidictionary
//    @PostMapping("/notebook")
//    public String saveOrUpdateNotebook(@Valid @ModelAttribute Notebook notebookAttribute, BindingResult bindingResult) {
//        if(bindingResult.hasErrors()){
//            bindingResult.getAllErrors().forEach(objectError -> {
//                log.debug(objectError.toString());
//            });
//            return NOTEBOOK_NOTEBOOKFORM;
//        }
//        Notebook notebook = notebookService.createOrUpdateNotebook(notebookAttribute);
//        return "redirect:/notebook/allnotebooks";
//    }
//
//    @GetMapping("/dictionaries")
//    public String showNotebooks(Model model) {
//        model.addAttribute("notebook", notebookService.findAllNotebooks());
//        return "notebook/allnotebooks";
//    }
//    @GetMapping("/notebook")
//    public String showNotebookForm(Model model) {
//        Notebook notebook = new Notebook();
//        model.addAttribute("notebook", notebook);
//        return NOTEBOOK_NOTEBOOKFORM;
//    }
//
//    @GetMapping("/notebook/{id}/update")
//    // Long id or String id
//    public String fillNotebookForm(@PathVariable Long id, Model model) {
//        model.addAttribute("notebook", notebookService.findById(id));
//        return NOTEBOOK_NOTEBOOKFORM;
//    }
//
//    @GetMapping("notebook/{id}/delete")
//    public String deleteById(@PathVariable Long id){
//        notebookService.deleteById(id);
//        return "redirect:/";
//    }
}
