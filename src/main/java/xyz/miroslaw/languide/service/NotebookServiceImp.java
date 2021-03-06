package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.NotebookRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class NotebookServiceImp implements NotebookService {

    private NotebookRepository notebookRepository;
    private UserService userService;

    @Autowired
    NotebookServiceImp(NotebookRepository notebookRepository, @Lazy UserService userService) {
        this.notebookRepository = notebookRepository;
        this.userService = userService;
    }

    @Override
    public Notebook findById(final Long id) {
        return Optional.ofNullable(notebookRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public void deleteById(String userName, final Long notebookID) {
        Optional<Notebook> notebook = notebookRepository.findByUserNameAndId(userName, notebookID);
        if (!notebook.isPresent()) {
            throw new NotFoundException("Not found. Id: " + notebookID);
        } else {
            notebookRepository.delete(notebook.get());
        }
    }

    @Override
    public Notebook createOrUpdateNotebook(Notebook notebook) {
        userService.getLoggedUser().ifPresent(notebook::setUser);
        return notebookRepository.save(notebook);
    }

    @Override
    public Set<Notebook> findUserNotebooks() {
        final Optional<Long> id = userService.getLoggedUser().map(User::getId);
        if (id.isPresent()) {
            return findUserNotebooks(id.get());
        } else {
            return new HashSet<Notebook>();
        }
    }

    @Override
    public Set<Notebook> findUserNotebooks(final Long id) {
        return notebookRepository.findAllByUserId(id);
    }

}
