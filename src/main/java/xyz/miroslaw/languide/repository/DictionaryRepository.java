package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.Dictionary;

interface DictionaryRepository extends CrudRepository<Dictionary, Long> {
}
