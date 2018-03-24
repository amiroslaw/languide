package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.DictionaryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DictionaryServiceImp implements DictionaryService {

    private DictionaryRepository dictionaryRepository;
    private UserService userService;

    @Autowired
    public DictionaryServiceImp(DictionaryRepository dictionaryRepository, UserService userService){
        this.dictionaryRepository = dictionaryRepository;
        this.userService = userService;
    }

    @Override
    public Dictionary findUserDictionary() {
        final Dictionary dictionary = userService.getLoggedUser()
                .map(User::getDictionary)
                .orElseThrow(() -> new NotFoundException("Not found."));
        return Optional.ofNullable(dictionaryRepository.findById(dictionary.getId()))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + dictionary.getId()));
    }

    @Override
    public void saveTranslation(Translation translation) {
        final Dictionary dictionary = findUserDictionary();
        dictionary.getTranslations().add(translation);
        dictionaryRepository.save(dictionary);
    }

    @Override
    public List<Translation> findUserTranslations() {
        return findUserDictionary().getTranslations();
    }
}
