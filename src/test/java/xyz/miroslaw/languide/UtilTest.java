package xyz.miroslaw.languide;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;
import xyz.miroslaw.languide.model.Role;
import xyz.miroslaw.languide.model.User;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UtilTest {
    public static final LocalDate localDate = LocalDate.of(2017, 10, 22);
    public static final Date date1 = Date.valueOf(localDate);
    public static final LocalDate localDate2 = LocalDate.of(2017, 11, 29);
    public static final Date date2 = Date.valueOf(localDate);
    public static final List<String> LANG_TEXT1 = Arrays.asList("Take a one egg");
    public static final List<String> LANG_TEXT2 = Arrays.asList("Weź jedno jajko");
    public  static final Article ARTICLE = Article.builder().title("cooking")
            .firstLanguage(LANG_TEXT1)
            .secondLanguage(LANG_TEXT2)
            .tag("cooking")
            .creationDate(date1)
            .hidden(false).build();
    public static final List<String> LANG_PLAYER1 = Arrays.asList("To remove the music player. Use Synaptic Package Manager or run command in terminal:");
    public static final List<String> LANG_PLAYER2 = Arrays.asList("Aby usunąć odtwarzacz muzyki. Użyj Synaptic Package Manager lub uruchom komendę w terminalu");
    public static final User USER = User.builder()
            .name("miro")
            .password("qwerty1")
            .email("miro@gmail.com")
            .build();
    public static final Notebook NOTEBOOK = Notebook.builder()
            .title("notebook")
            .description("first notebook")
            .user(USER)
            .build();
    public  static final Article ARTICLE_PLAYER = Article.builder().title("Music player")
            .firstLanguage(LANG_PLAYER1)
            .secondLanguage(LANG_PLAYER2)
            .notebook(NOTEBOOK)
            .tag("music")
            .creationDate(date2)
            .hidden(true).build();

    public  static final ArticleCommand ARTICLE_COMMAND = new ArticleCommand("cooking", "Take a one egg", "Weź jedno jajko",  "Cooking", date1, null);

    public static final List<Article> ARTICLE_LIST = new ArrayList<>(Arrays.asList(ARTICLE, ARTICLE_PLAYER));
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}