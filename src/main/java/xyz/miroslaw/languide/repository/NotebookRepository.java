package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.Notebook;

import java.util.Set;

public interface NotebookRepository extends CrudRepository<Notebook, Long> {
    Set<Notebook> findAllByUserName(String userName);
}
