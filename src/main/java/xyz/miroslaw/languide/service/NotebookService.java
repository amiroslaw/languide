package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Notebook;

import java.util.Set;

public interface NotebookService {

    Iterable<Notebook> findAllNotebooks();
    Set<Notebook> findUserNotebooks(String userName);
    Notebook findById(Long id);
    void deleteById(Long id);
    Notebook createNotebook(Notebook notebook);
}
