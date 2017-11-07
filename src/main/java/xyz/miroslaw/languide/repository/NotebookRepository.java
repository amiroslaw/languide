package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.Notebook;

interface NotebookRepository extends CrudRepository<Notebook, Long> {
}
