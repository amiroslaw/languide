package xyz.miroslaw.languide;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.Role;
import xyz.miroslaw.languide.model.User;
import xyz.miroslaw.languide.service.ArticleService;
import xyz.miroslaw.languide.service.NotebookService;
import xyz.miroslaw.languide.service.UserService;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@SpringBootApplication
public class LanguideApplication {

    public static void main(String[] args) {
        SpringApplication.run(LanguideApplication.class, args);
    }

    @Component
    public class DataLoader implements ApplicationRunner {
        private ArticleService articleService;
        private UserService userService;
        private NotebookService notebookService;

        @Autowired
        public DataLoader(NotebookService notebookService, UserService userService, ArticleService articleService) {
            this.notebookService = notebookService;
            this.articleService = articleService;
            this.userService = userService;
        }

        @Override
        public void run(ApplicationArguments args) {

//            Set<Notebook> notebooksSet = new HashSet<>();
//            notebooksSet.add(notebook);

            Date date = Calendar.getInstance().getTime();
            Collection<Role> roleUser = Arrays.asList(new Role("ROLE_USER"));
            User user = User.builder().name("arek").password("gracz").roles(roleUser).build();
            user = userService.createUser(user);

            Notebook publicNotebook = Notebook.builder().title("public").isPublic(true).description("public notebook for no register users").build();
            notebookService.createNotebook(publicNotebook);

            Notebook notebook = Notebook.builder().title("DB notebook").isPublic(true).description("first notebook for testing").user(user).build();
            Notebook notebook2 = Notebook.builder().title("DB notebook2").isPublic(true).description("second notebook for testing")
                    .user(user).build();
            notebookService.createNotebook(notebook);
            notebookService.createNotebook(notebook2);
            Article article1 = Article.builder().title("DB article1")
                    .firstLanguage(Arrays.asList("a", "b")).secondLanguage(Arrays.asList("c", "d"))
                    .tag("tag1")
                    .creationDate(date)
                    .isPublic(true)
                    .notebook(notebook)
                    .build();
            Article article2 = Article.builder().title("DB article2")
                    .firstLanguage(Arrays.asList("ac", "bc")).secondLanguage(Arrays.asList("cc", "dc"))
                    .tag("tag2")
                    .creationDate(date)
                    .isPublic(true)
                    .notebook(notebook)
                    .build();
            articleService.createArticle(article1);
            articleService.createArticle(article2);

            Notebook notebookTest = Notebook.builder().title("DB notebook").isPublic(true).description("first notebook for testing").user(user).article(article1).build();
            Notebook notebookTest2 = Notebook.builder().title("DB notebook").isPublic(true).description("first notebook for testing").user(user).articles(Arrays.asList(article1, article2)).build();
//            Article.builder().title().firstLanguage(Arrays.asList("ac", "bc")).secondLanguage(Arrays.asList("cc", "dc")).tag().creationDate(date).isPublic(true).notebook(notebook).build();

        }

    }
}

