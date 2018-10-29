package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.service.TranslationServiceImp;

import java.util.Collections;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TranslationControllerTest {

    @Mock
    private TranslationServiceImp serviceImp;
    @InjectMocks
    private TranslationController controller;
    private MockMvc mvc;

    private Translation translation;
    private static final String TRANSLATION_FORM = "dictionary/translationform";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

        translation = Translation.builder()
                .id(1L)
                .source("kot")
                .articleTranslation("cat")
                .build();
    }

    @Test
    public void saveOrUpdateTranslation_shouldRedirectToDictionary() throws Exception {
        when(serviceImp.createOrUpdateTranslation(any(Translation.class))).thenReturn(translation);

        mvc.perform(post("/user/1/translation")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("source", "kot")
                .param("articleTranslation", "cat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/1/dictionary"));

        verify(serviceImp, times(1)).createOrUpdateTranslation(any(Translation.class));
    }

    @Test
    public void saveOrUpdateTranslation_shouldRedirectToForm_blankSource() throws Exception {
        when(serviceImp.createOrUpdateTranslation(any(Translation.class))).thenReturn(translation);

        mvc.perform(post("/user/1/translation")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("source", "")
                .param("articleTranslation", "cat"))
                .andExpect(status().isOk())
                .andExpect(view().name(TRANSLATION_FORM));

        verify(serviceImp, times(0)).createOrUpdateTranslation(any(Translation.class));
    }


    @Test
    public void saveTranslation() throws Exception {
        when(serviceImp.createOrUpdateTranslation(any(Translation.class))).thenReturn(translation);

        mvc.perform(post("/user/1/translation/2")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("source", "kot")
                .param("articleTranslation", "cat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/1/article/2"));

        verify(serviceImp, times(1)).createOrUpdateTranslation(any(Translation.class));
    }

    @Test
    public void showNotebookForm() throws Exception {

        mvc.perform(get("/user/1/translation"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("translation"))
                .andExpect(view().name(TRANSLATION_FORM));

    }

    @Test
    public void fillTranslationForm() throws Exception {
        when(serviceImp.findById(anyLong())).thenReturn(translation);

        mvc.perform(get("/user/1/translation/2/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("translation"))
                .andExpect(view().name(TRANSLATION_FORM));

        verify(serviceImp, times(1)).findById(2L);
    }

    @Test
    public void deleteTranslationById() throws Exception {
        doNothing().when(serviceImp).deleteById(1L);

        mvc.perform(get("/user/1/translation/1/delete", anyLong())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(view().name("redirect:/user/1/dictionary"))
                .andExpect(status().is3xxRedirection());

        verify(serviceImp, times(1)).deleteById(1L);
    }
}