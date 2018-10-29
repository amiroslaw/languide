package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.service.DictionaryServiceImp;
import xyz.miroslaw.languide.service.TranslationServiceImp;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DictionaryControllerTest {

    @Mock
    private DictionaryServiceImp serviceImp;
    @InjectMocks
    private DictionaryController controller;
    private MockMvc mvc;
    private Translation translation;
    private Dictionary dictionary;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

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
    public void showTranslations() throws Exception {
        when(serviceImp.findUserTranslations()).thenReturn(Collections.singletonList(translation));

        mvc.perform(get("/user/1/dictionary"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("translations"))
                .andExpect(view().name("dictionary/alltranslations"));

        verify(serviceImp, times(1)).findUserTranslations();
    }
}