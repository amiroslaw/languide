package xyz.miroslaw.languide.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.repository.TranslationRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TranslationServiceImpTest {

    @Mock
    DictionaryService dictionaryService;
    @Mock
    TranslationRepository repository;
    @InjectMocks
    TranslationServiceImp service;
    private Dictionary dictionary;
    private Translation translation;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        translation = Translation.builder()
                .id(1L)
                .source("kot")
                .articleTranslation("cat")
                .build();
        dictionary = Dictionary.builder().id(1L)
                .translations(Collections.singletonList(translation))
                .build();
    }

    @Test
    public void findById_shouldFind() {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(translation));

        Translation translation = service.findById(anyLong());

        assertThat(translation).isNotNull().isEqualTo(translation);
        verify(repository, times(1)).findById(anyLong());
    }
    @Test(expected = NotFoundException.class)
    public void findById_shouldThrowNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(null);

        service.findById(anyLong());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void deleteById_shouldDelete() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(translation));
        doNothing().when(repository).delete(any(Translation.class));

        service.deleteById(anyLong());

        verify(repository, times(1)).delete(any(Translation.class));
    }

    @Test(expected = NotFoundException.class)
    public void deleteById_shouldNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(any(Translation.class));

        service.deleteById(anyLong());

        verify(repository, times(1)).delete(any(Translation.class));
    }

    @Test
    public void createOrUpdateTranslation_shouldSave() {
        translation.setDictionary(dictionary);
        when(repository.save(any(Translation.class))).thenReturn(translation);
        when(dictionaryService.findUserDictionary()).thenReturn(dictionary);

        Translation result = service.createOrUpdateTranslation(translation);

        assertThat(result).isEqualTo(translation);
        verify(repository, times(1)).save(any(Translation.class));
    }


}