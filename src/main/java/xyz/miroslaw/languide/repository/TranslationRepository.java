package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.Translation;

import java.util.List;
import java.util.Optional;

public interface TranslationRepository extends CrudRepository<Translation, Long> {
    List<Translation> findAllByDictionaryId(Long id);
}
