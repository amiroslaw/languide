package xyz.miroslaw.languide.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.miroslaw.languide.UtilTest;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.Translation;
import xyz.miroslaw.languide.service.ArticleServiceImp;
import xyz.miroslaw.languide.service.UserServiceImpl;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static xyz.miroslaw.languide.UtilTest.NOTEBOOK;

public class ArticleControllerTest {
    @Mock
    private ArticleServiceImp serviceImp;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private ArticleController controller;
    private MockMvc mvc;
    HashSet<Notebook> notebooks;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        notebooks = new HashSet<>();
        notebooks.add(NOTEBOOK);
    }

    @Test
    public void pair_shouldShowArticle() throws Exception {

        when(serviceImp.createArticle(any(Article.class))).thenReturn(UtilTest.ARTICLE);
        when(userService.getUserNotebooks()).thenReturn(notebooks);

        mvc.perform(post("/user/miro/article").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstLanguage", "egg")
                .param("secondLanguage", "jajko"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("notebooks"))
                .andExpect(view().name("article/pair"));

        verify(serviceImp, times(1)).createArticle(any(Article.class));
        verify(userService, times(1)).getUserNotebooks();

    }

    @Test
    public void updateArticleDescription() throws Exception {
        doNothing().when(serviceImp).updateArticle(any(Article.class), anyLong());

        mvc.perform(post("/user/miro/article/1/updatedescription")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("title", "art")
                .param("hidden", "true")
                .param("tag", "animals"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/miro/article/1"));

        verify(serviceImp, times(1)).updateArticle(any(Article.class), anyLong());
    }

    @Test
    @Ignore
    public void updateArticle() throws Exception {
//        when(serviceImp.updateArticle(any(Article.class), anyLong(), anyLong())).thenReturn(NOTEBOOK);
        doNothing().when(serviceImp).updateArticle(any(Article.class), anyLong(), anyLong());

        mvc.perform(post("/user/miro/article/1/update?notebook=2")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("title", "art")
                .param("hidden", "true")
                .param("tag", "animals"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));

        verify(serviceImp, times(1)).updateArticle(any(Article.class), anyLong(), anyLong());
    }

    @Test
    public void showArticleForm() throws Exception {
        when(serviceImp.findById(anyLong())).thenReturn(UtilTest.ARTICLE);
        when(userService.getUserNotebooks()).thenReturn(notebooks);

        mvc.perform(get("/user/miro/article/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("notebooks"))
                .andExpect(view().name("article/articleform"));

        verify(serviceImp, times(1)).findById(anyLong());
        verify(userService, times(1)).getUserNotebooks();
    }

    @Test
    public void showArticle() throws Exception {
        when(serviceImp.findById(anyLong())).thenReturn(UtilTest.ARTICLE);

        mvc.perform(get("/user/miro/article/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("article"))
                .andExpect(model().attributeExists("translation"))
                .andExpect(view().name("article/view"));

        verify(serviceImp, times(1)).findById(anyLong());
    }

    @Test
    public void showAllArticles() throws Exception {
        when(serviceImp.findAllPublicArticles()).thenReturn(UtilTest.ARTICLE_LIST);

        mvc.perform(get("/articles/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(view().name("article/allarticles"));

        verify(serviceImp, times(1)).findAllPublicArticles();
    }

    @Test
    public void showUserArticles_shouldFindAllUserArticles() throws Exception {
        when(serviceImp.findAllArticlesByNotebookId(anyLong())).thenReturn(UtilTest.ARTICLE_LIST);

        mvc.perform(get("/user/miro/articles?id=3"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
//                .andExpect(model().attributeExists("userId"))
                .andExpect(view().name("article/userarticles"));

        verify(serviceImp, times(1)).findAllArticlesByNotebookId(anyLong());
    }

    @Test
    @Ignore
    public void showUserArticles_shouldFindAllNotebookArticles() throws Exception {
        when(serviceImp.findArticlesByUserId(anyLong())).thenReturn(UtilTest.ARTICLE_LIST);

        mvc.perform(get("/user/miro/articles?id=0"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("userId"))
                .andExpect(view().name("article/userarticles"));

        verify(serviceImp, times(1)).findArticlesByUserId(anyLong());
    }

    @Test
    public void deleteById_shouldRedirectToAllUserArticles() throws Exception {
        doNothing().when(serviceImp).deleteById(1L);

        mvc.perform(get("/user/miro/article/1/delete?id=0")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(view().name("redirect:/user/miro/articles?id=0"))
                .andExpect(status().is3xxRedirection());

        verify(serviceImp, times(1)).deleteById(1L);
    }

    @Test
    public void deleteById_shouldRedirectToAllNotebookArticles() throws Exception {
        doNothing().when(serviceImp).deleteById(1L);

        mvc.perform(get("/user/miro/article/1/delete?id=3")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(view().name("redirect:/user/miro/articles?id=3"))
                .andExpect(status().is3xxRedirection());

        verify(serviceImp, times(1)).deleteById(1L);
    }
}