package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.repository.TranslationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TranslationServiceImp implements TranslationService{

    private TranslationRepository translationRepository;
    private DictionaryService dictionaryService;

    @Autowired
    public TranslationServiceImp(TranslationRepository translationRepository, DictionaryService dictionaryService){
        this.translationRepository = translationRepository;
        this.dictionaryService = dictionaryService;
    }

    @Override
    public Translation findById(Long id) {
        return Optional.ofNullable(translationRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));

    }

    @Override
    public void deleteById(Long id) {
        Optional<Translation> article = translationRepository.findById(id);
        if (!article.isPresent()) {
            throw new NotFoundException("Not found. Id: " + id);
        } else {
            translationRepository.delete(article.get());
        }
    }

    @Override
    public Translation createOrUpdateTranslation(final Translation translation) {
        translation.setDictionary(dictionaryService.findUserDictionary());
        return translationRepository.save(translation);
    }

    @Override
    public List<Translation> findDictionaryTranslations(Long id) {
        return translationRepository.findAllByDictionaryId(id);
    }
}
