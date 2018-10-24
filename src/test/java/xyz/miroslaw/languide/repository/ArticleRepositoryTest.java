package xyz.miroslaw.languide.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.miroslaw.languide.UtilTest;
import xyz.miroslaw.languide.model.Article;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ArticleRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ArticleRepository repository;
   Article article;
    @Before
    public void setup(){
        article = Article.builder().title("cooking")
                .firstLanguage(Collections.singleton("dog"))
                .secondLanguage(Collections.singleton("kot"))
                .tag("cooking")
                .creationDate(null)
                .hidden(false).build();
    }
    @Test
    public void findByUsernameShouldReturnUser() {
//        entityManager.persist(UtilTest.ARTICLE);
//        List<Article> articles = repository.findArticlesByUserId(1L);
//
//        assertThat(articles).isNotEmpty();
    }
    @Test
    public void findAllByHiddenFalse_shouldFindOne() {
        entityManager.persist(article);
        List<Article> articles = repository.findAllByHiddenFalse();

        assertThat(articles).isNotEmpty().hasSize(1);
    }
    @Test
    public void findArticleByTitle_shouldFind() {
//        entityManager.persist(UtilTest.ARTICLE);
//        Optional<Article> articles = repository.findArticleByTitle("cooking");
//
//        assertThat(articles).isNotEmpty();
    }
}
