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
import xyz.miroslaw.languide.repository.ArticleRepository;
import xyz.miroslaw.languide.repository.UserRepository;
import xyz.miroslaw.languide.service.ArticleService;
import xyz.miroslaw.languide.service.NotebookService;
import xyz.miroslaw.languide.service.UserService;

import java.util.*;

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

            Collection<Role> roleUser = Arrays.asList(new Role("ROLE_USER"));
            User user = new User("arek", "gracz", roleUser);
            user = userService.createUser(user);

            Notebook publicNotebook = new Notebook("Public Notebook", true, "public notebook for no register users");
            notebookService.createNotebook(publicNotebook);

            Notebook notebook = new Notebook("DB notebook", true, "first notebook for testing");
            notebook.setUser(user);
            Notebook notebook2 = new Notebook("DB notebook2", true, "second notebook for testing");
            notebook2.setUser(user);
            notebookService.createNotebook(notebook);
            notebookService.createNotebook(notebook2);
            Date date = Calendar.getInstance().getTime();
            Article article1 = new Article("DB article1", Arrays.asList("a", "b"), Arrays.asList("c", "d"), "tag1", date, notebook);
            Article article2 = new Article("DB article2", Arrays.asList("aa", "ba"), Arrays.asList("ca", "da"), "tag2", date, notebook);
            articleService.createArticle(article1);
            articleService.createArticle(article2);
        }

    }
}

