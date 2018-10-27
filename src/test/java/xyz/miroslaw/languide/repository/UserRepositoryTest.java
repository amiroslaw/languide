package xyz.miroslaw.languide.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import xyz.miroslaw.languide.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository repository;

    private User user;

    @Before
    public void setUp() throws Exception {
        user = User.builder()
                .name("miro")
                .password("qwerty1")
                .email("miro@gmail.com")
                .build();
    }

    @Test
    public void findByName_shouldFind(){
        entityManager.persist(user);

        Optional<User> user = repository.findByName("miro");

        assertThat(user).isPresent();
        assertThat(user.get().getPassword()).contains("qwerty1");
    }

    @Test
    public void findByEmail_shouldFind(){
        entityManager.persist(user);

        Optional<User> user = repository.findByEmail("miro@gmail.com");

        assertThat(user).isPresent();
        assertThat(user.get().getPassword()).contains("qwerty1");
    }
}