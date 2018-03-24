package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;

import java.util.List;

public interface DictionaryService {
    Dictionary findUserDictionary();
    void saveTranslation(Translation translation);
    List<Translation> findUserTranslations();
}
