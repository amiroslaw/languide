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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ArticleServiceImpTest {

    @Mock
    private ArticleRepository repository;
    @InjectMocks
    private ArticleServiceImp service;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findArticles() throws Exception {
        //TODO findPublicArticles in service imp test
    }

    @Test
    public void findById_shouldFindArticle() throws Exception {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(UtilTest.ARTICLE));

        service.findById(anyLong());

        assertThat(UtilTest.ARTICLE).isNotNull();
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
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(UtilTest.ARTICLE));
        doNothing().when(repository).delete(any(Article.class));

        service.deleteById(anyLong());

        verify(repository, times(1)).delete(any(Article.class));
    }

    @Test(expected = NotFoundException.class)
    public void deleteById_shouldNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(any(Article.class));

        service.deleteById(anyLong());

        verify(repository, times(1)).delete(any(Article.class));
    }

//    @Test
//    public void createArticle_shouldCreateArticle() throws Exception {
//        when(repository.save(any(Article.class))).thenReturn(UtilTest.ARTICLE);
//
//        service.createOrUpdateArticle(UtilTest.ARTICLE_COMMAND);
//
//        verify(repository, times(1)).save(any());
//    }

    @Test
    public void updateArticle_shouldUpdateArticle() throws Exception {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(UtilTest.ARTICLE));
        when(repository.save(any(Article.class))).thenReturn(UtilTest.ARTICLE);

//        service.updateArticle(anyLong());

        verify(repository, times(1)).save(any(Article.class));
    }

    @Test(expected = NotFoundException.class)
    public void updateArticle_shouldNotFound() throws Exception {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(repository.save(any(Article.class))).thenReturn(UtilTest.ARTICLE);

//        service.updateArticle(anyLong());

        verify(repository, times(1)).save(any(Article.class));
    }

//    @Test
//    public void convertToList_shouldConvertCorrectly() throws Exception {
//        final String LANGUAGE_TEXT = "Aliquam a dignissim urna -at lacinia turpis. Aliquam erat volutpat? Vivamus tempor magna vel libero aliquet? Suscipit semper at lacinia a.";
//        final List<String> SPLIT_TEXT = Arrays.asList(
//                "Aliquam a dignissim urna ",
//                "-at lacinia turpis. ",
//                "Aliquam erat volutpat? ",
//                "Vivamus tempor magna vel libero aliquet? ",
//                "Suscipit semper at lacinia a.");
//
//        List<String> result = service.convertToList(LANGUAGE_TEXT);
//
//        assertThat(result).isEqualTo(SPLIT_TEXT)
//                .hasSize(5);
//    }
}