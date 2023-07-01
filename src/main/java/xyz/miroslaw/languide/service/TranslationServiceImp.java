package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.repository.TranslationRepository;

import java.util.List;

@Service
public class TranslationServiceImp implements TranslationService {

    private final TranslationRepository translationRepository;
    private final DictionaryService dictionaryService;

    @Autowired
    public TranslationServiceImp(TranslationRepository translationRepository, DictionaryService dictionaryService) {
        this.translationRepository = translationRepository;
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Translation findById(final Long id) {
        return translationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public void deleteById(final Long id) {
        final Translation translation = translationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
        translationRepository.delete(translation);
    }

    @Override
    public Translation createOrUpdateTranslation(final Translation translation) {
        translation.setDictionary(dictionaryService.findUserDictionary());
        return translationRepository.save(translation);
    }

    @Override
    public List<Translation> findDictionaryTranslations(final Long id) {
        return translationRepository.findAllByDictionaryId(id);
    }
}
