package xyz.miroslaw.languide.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;


import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class TranslationRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TranslationRepository repository;
    private Dictionary dictionary;
    private Translation translation;

    @Before
    public void setUp() throws Exception {
        dictionary = new Dictionary();
        translation = new Translation().builder()
                .source("cat")
                .googleTranslation(Collections.singletonList("kot"))
                .dictionary(dictionary)
                .build();

//                .translations(Collections.singletonList(translation))
//                .build();
    }
    @Test
    public void findAllByDictionaryId_shouldFindOne(){
        Long dictionaryID = (Long) entityManager.persistAndGetId(dictionary);
        entityManager.persist(translation);

        List<Translation> translations =  repository.findAllByDictionaryId(dictionaryID);

        assertThat(translations).hasSize(1);
        assertThat(translations.get(0).getSource()).contains("cat");
    }


}