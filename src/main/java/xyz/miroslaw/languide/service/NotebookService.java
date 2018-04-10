package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Notebook;

import java.util.Set;

public interface NotebookService {
    Set<Notebook> findUserNotebooks();
    Set<Notebook> findUserNotebooks(Long id);
    Notebook findById(Long id);
    void deleteById(String userName, Long id);
    Notebook createOrUpdateNotebook(Notebook notebook);
}
