package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Notebook;

import java.util.Optional;
import java.util.Set;

public interface NotebookService {

    Iterable<Notebook> findAllNotebooks();
//    Set<Notebook> findUserNotebooks(String userName);
    Set<Notebook> findUserNotebooks(Long id);
    Optional<Notebook> findPublicNotebook();
    Notebook findById(Long id);
    void deleteById(Long id);
    Notebook createNotebook(Notebook notebook);
}
