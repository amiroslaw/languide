package xyz.miroslaw.languide.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.repository.DictionaryRepository;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DictionaryServiceImpTest {
    @Mock
    UserService userService;
    @Mock
    DictionaryRepository repository;
    @InjectMocks
    DictionaryServiceImp service;
    private Dictionary dictionary;
    private Translation translation;
    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        translation = Translation.builder()
                .id(1L)
                .source("kot")
                .articleTranslation("cat")
                .dictionary(dictionary)
                .build();
        dictionary = Dictionary.builder().id(1L)
                .translations(Collections.singletonList(translation))
                .build();
        user = User.builder()
                .name("miro")
                .password("qwerty1")
                .email("miro@gmail.com")
                .build();
    }

    @Test
    public void findUserDictionary_shouldFind() {
        user.setDictionary(dictionary);
        when(userService.getLoggedUser()).thenReturn(Optional.ofNullable(user));
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(dictionary));


        Dictionary dictionary = service.findUserDictionary();

        assertThat(dictionary).isEqualTo(dictionary);
        verify(repository, times(1)).findById(1L);
    }

    @Test(expected = NotFoundException.class)
    public void findUserDictionary_shouldThrowNotFoundDictionary() {
        user.setDictionary(dictionary);
        when(userService.getLoggedUser()).thenReturn(Optional.ofNullable(user));
        when(repository.findById(1L)).thenReturn(null);

        service.findUserDictionary();

        verify(repository, times(1)).findById(1L);
    }
    @Test(expected = NotFoundException.class)
    public void findUserDictionary_shouldThrowNotFoundDictionaryInRepository() {
        when(userService.getLoggedUser()).thenReturn(Optional.ofNullable(user));
        when(repository.findById(1L)).thenReturn(Optional.ofNullable(dictionary));

        service.findUserDictionary();

        verify(repository, times(1)).findById(1L);
    }

}