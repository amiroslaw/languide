package xyz.miroslaw.languide.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import xyz.miroslaw.languide.UtilTest;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticleServiceImpTest {
    @Mock
    private NotebookService notebookService;
    @Mock
    private ArticleRepository repository;
    @InjectMocks
    private ArticleServiceImp service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllPublicArticles_shouldFindOneArticle() throws Exception {
        when(repository.findAllByHiddenFalse()).thenReturn(new ArrayList<>(Arrays.asList(UtilTest.ARTICLE)));

        List<Article> articleList = service.findAllPublicArticles();

        assertThat(articleList).hasSize(1).contains(UtilTest.ARTICLE);
        verify(repository, times(1)).findAllByHiddenFalse();
    }
    @Test
    public void findAllArticlesByNotebookId_shouldFindOneArticle() {
        when(repository.findAllByNotebookId(anyLong())).thenReturn(new ArrayList<>(Arrays.asList(UtilTest.ARTICLE)));

        List<Article> articleList = service.findAllArticlesByNotebookId(2L);

        assertThat(articleList).contains(UtilTest.ARTICLE);
        verify(repository, times(1)).findAllByNotebookId(anyLong());
    }

    @Test
    public void findById_shouldFindArticle() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(UtilTest.ARTICLE));

        Article article = service.findById(anyLong());

        assertThat(article).isNotNull().isEqualTo(UtilTest.ARTICLE);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void findById_shouldThrowNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(null);

        service.findById(anyLong());

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void findArticlesByUserId_shouldNotFindAnyArticle() {
        when(repository.findArticlesByUserId(anyLong())).thenReturn(Collections.emptyList());

        List<Article> articleList = service.findArticlesByUserId(1L);

        assertThat(articleList).isEmpty();
        verify(repository, times(1)).findArticlesByUserId(anyLong());
    }


    @Test
    public void deleteById_shouldDelete() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.ofNullable(UtilTest.ARTICLE));
        doNothing().when(repository).delete(any(Article.class));

        service.deleteById(anyLong());

        verify(repository, times(1)).delete(any(Article.class));
    }

    @Test(expected = NotFoundException.class)
    public void deleteById_shouldNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(any(Article.class));

        service.deleteById(anyLong());
//
        verify(repository, times(1)).delete(any(Article.class));
    }

    @Test
    public void createArticle_shouldCreateArticle() throws Exception {
        when(repository.save(any(Article.class))).thenReturn(UtilTest.ARTICLE);

       Article article =  service.createArticle(UtilTest.ARTICLE);

        assertThat(article).isEqualTo(UtilTest.ARTICLE);
        verify(repository, times(1)).save(any());
    }

    @Test
    public void updateArticle_shouldUpdateArticle() throws Exception {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(UtilTest.ARTICLE));
        when(repository.save(any(Article.class))).thenReturn(UtilTest.ARTICLE);

        service.updateArticle(UtilTest.ARTICLE, 1L);

        verify(repository, times(1)).save(any(Article.class));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void updateArticle_shouldNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(null);
        when(repository.save(any(Article.class))).thenReturn(UtilTest.ARTICLE);

        service.updateArticle(UtilTest.ARTICLE, 1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Article.class));
    }


}