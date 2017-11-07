package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Notebook;

import java.util.Set;

public interface NotebookService {
    Set<Notebook> getNotebooks();
    Notebook findById(Long id);
    void deleteById(Long id);
    void createNotebook();
}
