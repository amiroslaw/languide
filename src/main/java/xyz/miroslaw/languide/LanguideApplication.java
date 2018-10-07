package xyz.miroslaw.languide;

import org.omg.IOP.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import xyz.miroslaw.languide.model.*;
import xyz.miroslaw.languide.model.Dictionary;
import xyz.miroslaw.languide.repository.TranslationRepository;
import xyz.miroslaw.languide.service.*;

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
        private DictionaryService dictionaryService;
        private TranslationService translationService;
        @Autowired
        TranslationRepository translationRepository;

        @Autowired
        DataLoader(TranslationService translationService, NotebookService notebookService, UserService userService, ArticleService articleService, DictionaryService dictionaryService) {
            this.notebookService = notebookService;
            this.translationService = translationService;
            this.articleService = articleService;
            this.userService = userService;
            this.dictionaryService = dictionaryService;
        }

        @Override
        public void run(ApplicationArguments args) {
            loadData();
        }

        private void loadData() {
            //            Set<Notebook> notebooksSet = new HashSet<>();
//            notebooksSet.add(notebook);

            Translation translation = Translation.builder().source("W końcu mam akt zgonu mojego ojca. Moja siostra była na tyle uprzejma, że wysłała jej zdjęcie, a ona prześle jej kopię pocztą. Myślałem, że urodził się w Hammond w stanie Indiana w jedynym szpitalu w okolicy.")
                    .source("Jednak certyfikat mówi, że urodził się w Illinois, po drugiej stronie granicy państwowej, więc musiało to być porody domowe. Wprowadziłem zmianę w drzewie genealogicznym, aby zachować dokładność.")
                    .articleTranslation("I finally have a death certificate of my father. My sister was kind enough to email a photo of it and she will send a copy of it through the mail. I thought he was born in Hammond, Indiana, in the only hospital in the area.")
                    .articleTranslation("However, the certificate says he was born in Illinois, just across the state border so it must have been a home birth. I made the change in the family tree to keep it accurate.")
                    .build();
            translationRepository.save(translation);
            Dictionary dictionary = Dictionary.builder().build();

            Date date = Calendar.getInstance().getTime();
            Collection<Role> roleUser = Arrays.asList(new Role("ROLE_USER"));
            User user = User.builder().name("qwer").password("qwer").roles(roleUser).dictionary(dictionary).build();
            user = userService.createUser(user);

            User user2 = User.builder().name("user").password("user").roles(roleUser).build();
            user2 = userService.createUser(user2);
            User userEmpty = User.builder().name("empty").password("empty").roles(roleUser).build();
            userService.createUser(userEmpty);

            Notebook publicNotebook = Notebook.builder().title("public").description("public notebook for no register user").user(user2).build();
            notebookService.createOrUpdateNotebook(publicNotebook);

            Notebook notebook = Notebook.builder().title("DB notebook").description("first notebook for user").user(user).build();
            Notebook notebook2 = Notebook.builder().title("DB notebook2").description("second notebook for user")
                    .user(user).build();
            notebookService.createOrUpdateNotebook(notebook);
            notebookService.createOrUpdateNotebook(notebook2);

            Notebook notebookTest = Notebook.builder().title("test").description("first notebook for qwer").user(user2).build();
            Notebook notebookTest2 = Notebook.builder().title("test private").description("second notebook for qwer").user(user2).build();
            notebookService.createOrUpdateNotebook(notebookTest);
            notebookService.createOrUpdateNotebook(notebookTest2);

            Article article1 = Article.builder().title("DB article1")
                    .firstLanguage(Arrays.asList("a", "b")).secondLanguage(Arrays.asList("c", "d"))
                    .tag("tag1")
                    .creationDate(date)
                    .hidden(false)
                    .notebook(notebook)
                    .build();
            Article article2 = Article.builder().title("DB article2")
                    .firstLanguage(Arrays.asList("ac", "bc")).secondLanguage(Arrays.asList("cc", "dc"))
                    .tag("tag2")
                    .creationDate(date)
                    .hidden(false)
                    .notebook(notebook2)
                    .build();
            Article articlePrivate = Article.builder().title("DB article private")
                    .firstLanguage(Arrays.asList("ac", "bc")).secondLanguage(Arrays.asList("cc", "dc"))
                    .tag("tag2")
                    .creationDate(date)
                    .hidden(true)
                    .notebook(notebook)
                    .build();
            articleService.createOrUpdateArticle(article1);
            articleService.createOrUpdateArticle(article2);
            articleService.createOrUpdateArticle(articlePrivate);
        }

    }
}

