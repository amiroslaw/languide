package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.repository.NotebookRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class NotebookServiceImp implements NotebookService {

    @Autowired
    private NotebookRepository notebookRepository;

    @Override
    public Iterable<Notebook> findAllNotebooks() {
        return notebookRepository.findAll();
    }
    @Override
//    public Set<Notebook> findUserNotebooks(String userName) {
//        return notebookRepository.findAllByUserName(userName);
//    }
    public Set<Notebook> findUserNotebooks(Long id) {
        return notebookRepository.findAllByUserId(id);
    }

    @Override
    public Optional<Notebook> findPublicNotebook() {return notebookRepository.findByUserNull();}

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
     return  notebookRepository.save(notebook);
    }
}
