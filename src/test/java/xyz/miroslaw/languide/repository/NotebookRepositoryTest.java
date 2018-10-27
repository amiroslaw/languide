package xyz.miroslaw.languide.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import xyz.miroslaw.languide.UtilTest;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.User;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
@DirtiesContext
public class NotebookRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private NotebookRepository repository;
    private Notebook notebook;
    private final String userName = "miro";
    private final String userEmail = "miro@gmail.com";
    private User user;

    @Before
    public void setup(){
        user = new User().builder()
                .name(userName)
                .email(userEmail)
                .password("qwery123")
                .build();

        notebook = Notebook.builder()
                .title("notebook")
                .description("first notebook")
                .user(user)
                .build();
    }
    @Test
    public void findAllByUserId_shouldFindOne(){
        entityManager.persist(user);
        entityManager.persist(notebook);

        Set<Notebook> notebooks = repository.findAllByUserName(userName);

        assertThat(notebooks).hasSize(1);
    }
    @Test
    public void findByUserNameAndAndId_shouldFind(){
        entityManager.persist(user);
        Long notebookID = (Long) entityManager.persistAndGetId(notebook);

        Optional<Notebook> notebook = repository.findAllByUserNameAndAndId(userName, notebookID);

        assertThat(notebook.get().getTitle()).isEqualTo("notebook");
        assertThat(notebook).isPresent();
    }
    @After
    public void clear(){
        entityManager.clear();
    }
}