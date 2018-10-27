package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.Notebook;

import java.util.Optional;
import java.util.Set;

public interface NotebookRepository extends CrudRepository<Notebook, Long> {
    Set<Notebook> findAllByUserName(String userName);
    Optional<Notebook> findByUserNameAndId(String userName, Long id);
    Set<Notebook> findAllByUserId(Long id);
}
