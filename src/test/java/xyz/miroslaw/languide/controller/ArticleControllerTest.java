package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class ArticleControllerTest {
    @InjectMocks
    private ArticleController controller;
    @Mock
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void pair_shouldShowArticle() throws Exception {
//        when(service.createArticle(any(ArticleCommand.class))).thenReturn(any(Article.class));

        mockMvc.perform(post("").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstLanguage", "egg")
                .param("secondLanguage", "jajko"))
                .andExpect(status().isCreated())
                .andExpect(view().name("article"));

//        verify(service, times(1)).createArticle(any(ArticleCommand.class));
    }
    @Test
    public void pair_shouldFailValidation() throws Exception {
//        when(service.createArticle(any(ArticleCommand.class))).thenReturn(any(Article.class));
        mockMvc.perform(post("").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                    .param("firstLanguage", "egg")
                .param("secondLanguage", ""))
                .andExpect(status().isBadRequest())
        ;
//                .andExpect(model().attributeExists("article"))
//                .andExpect(view().name("index"));

    }
    @Test
    public void articleCommandModel() {
    }

    @Test
    public void pair() {
    }

    @Test
    public void updateArticleDescription() {
    }

    @Test
    public void updateArticle() {
    }

    @Test
    public void showArticleForm() {
    }

    @Test
    public void showArticle() {
    }

    @Test
    public void showAllArticles() {
    }

    @Test
    public void showUserArticles() {
    }

    @Test
    public void deleteById() {
    }
}