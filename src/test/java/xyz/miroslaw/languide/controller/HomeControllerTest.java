package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.service.ArticleService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest {

    @InjectMocks
    private HomeController controller;
    @Mock
    private ArticleService service;
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getHomePage_shouldShowHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

//    @Test
//    public void pair_shouldShowArticle() throws Exception {
//        when(service.createArticle(any(ArticleCommand.class))).thenReturn(any(Article.class));
//
//        mockMvc.perform(post("").contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("firstLanguage", "egg")
//                .param("secondLanguage", "jajko"))
//                .andExpect(status().isCreated())
//                .andExpect(view().name("article"));
//
//        verify(service, times(1)).createArticle(any(ArticleCommand.class));
//    }
//
//    @Test
//    public void pair_shouldFailValidation() throws Exception {
//        when(service.createArticle(any(ArticleCommand.class))).thenReturn(any(Article.class));
//
//        mockMvc.perform(post("").contentType(MediaType.APPLICATION_FORM_URLENCODED)
////                    .param("firstLanguage", "egg")
//                .param("secondLanguage", ""))
//                .andExpect(status().isBadRequest())
//        ;
////                .andExpect(model().attributeExists("article"))
////                .andExpect(view().name("index"));
//
//    }

    @Test
    public void getArticles() throws Exception {
    }

}