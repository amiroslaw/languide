package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import xyz.miroslaw.languide.model.Article;

import xyz.miroslaw.languide.service.ArticleService;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class HomeControllerTest {
    private static final LocalDate localDate = LocalDate.of(2017, 10, 22);
    private static final Date date1 = Date.valueOf(localDate);
    private static final Article ARTICLE = new Article("cooking", "Take a one egg", "Weź jedno jajko", "Cooking", date1, null);

    @InjectMocks
    private  HomeController controller;
    @Mock
    private ArticleService service;
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getHomePage_shouldReturnHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void pair() throws Exception {
        doNothing().when(service).saveArticle(any(Article.class));

        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(view().name("article"));
    }

    @Test
    public void getArticles() throws Exception {
    }

}