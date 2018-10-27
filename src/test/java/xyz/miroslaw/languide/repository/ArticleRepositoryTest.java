package xyz.miroslaw.languide.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class ArticleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ArticleRepository repository;
    private Article article;
    private Notebook notebook;
    private Article hiddenArticle;
    @Before
    public void setup(){
        article = Article.builder().title("cooking")
                .firstLanguage(Collections.singleton("dog"))
                .secondLanguage(Collections.singleton("kot"))
                .tag("cooking")
                .creationDate(null)
                .hidden(false).build();
        notebook = Notebook.builder()
                .title("notebook")
                .description("first notebook")
                .build();
        hiddenArticle = Article.builder().title("Music player")
                .tag("music")
                .hidden(true)
                .build();
    }
    @Test
    @Ignore
    public void findAllByNotebookId_shouldFind() {
        hiddenArticle.setNotebook(notebook);
//        notebook.setArticles(Collections.singleton(article));
        Long notebookID = (Long) entityManager.persistAndGetId(notebook);
        entityManager.persist(hiddenArticle);
        List<Article> articles = repository.findAllByNotebookId(1L);

        assertThat(articles).isNotEmpty();
    }
    @Test
    public void findAllByHiddenFalse_shouldFindOne() {
        entityManager.persist(article);
        List<Article> articles = repository.findAllByHiddenFalse();

        assertThat(articles).isNotEmpty().hasSize(1);
    }
    @Test
    public void findAllByHiddenFalse_shouldNotFind() {
        entityManager.persist(hiddenArticle);
        entityManager.flush();
        List<Article> articles = repository.findAllByHiddenFalse();

        assertThat(articles).isEmpty();
    }
    @After
    public void clear(){
        entityManager.clear();
    }
}
