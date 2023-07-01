package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.NotebookRepository;

import java.util.HashSet;
import java.util.Set;

@Service
public class NotebookServiceImp implements NotebookService {

    private final NotebookRepository notebookRepository;
    private final UserService userService;

    @Autowired
    NotebookServiceImp(NotebookRepository notebookRepository, @Lazy UserService userService) {
        this.notebookRepository = notebookRepository;
        this.userService = userService;
    }

    @Override
    public Notebook findById(final Long id) {
        return notebookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public void deleteById(String userName, final Long notebookID) {
        Notebook notebook = notebookRepository.findByUserNameAndId(userName, notebookID)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + notebookID));
        notebookRepository.delete(notebook);
    }

    @Override
    public Notebook createOrUpdateNotebook(Notebook notebook) {
        userService.getLoggedUser().ifPresent(notebook::setUser);
        return notebookRepository.save(notebook);
    }

    @Override
    public Set<Notebook> findUserNotebooks() {
        return userService.getLoggedUser().map(User::getId)
                .map(this::findUserNotebooks)
                .orElse(new HashSet<>());
    }

    @Override
    public Set<Notebook> findUserNotebooks(final Long id) {
        return notebookRepository.findAllByUserId(id);
    }

}
