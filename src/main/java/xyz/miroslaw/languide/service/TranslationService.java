package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Translation;

import java.util.List;

public interface TranslationService {
    Translation findById(Long id);
    void deleteById(Long id);
    Translation createOrUpdateTranslation(Translation translation);
    List<Translation> findDictionaryTranslations(Long id);
}
