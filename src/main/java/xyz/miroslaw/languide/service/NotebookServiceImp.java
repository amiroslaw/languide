package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private NotebookRepository notebookRepository;
    @Autowired
    private UserService userService;

    @Override
    public Notebook findById(Long id) {
        return Optional.ofNullable(notebookRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        Optional<Notebook> article = notebookRepository.findById(id);
        if (!article.isPresent()) {
            throw new NotFoundException("Not found. Id: " + id);
        } else {
            notebookRepository.delete(article.get());
        }
    }

    @Override
    public Notebook createNotebook(Notebook notebook) {
        userService.getLoggedUser().ifPresent(e -> notebook.setUser(e));
        return notebookRepository.save(notebook);
    }
    @Override
    public Set<Notebook> findUserNotebooks() {
        Optional<Long> id = userService.getLoggedUser().map(User::getId);
        if (id.isPresent()) {
            return findUserNotebooks(id.get());
        } else{
            return new HashSet<>();
        }
    }
    @Override
    public Set<Notebook> findUserNotebooks(Long id) {
        return notebookRepository.findAllByUserId(id);
    }
    //    }
    //        return notebookRepository.findAllByUserName(userName);
    //    public Set<Notebook> findUserNotebooks(String userName) {
    //    @Override
}
