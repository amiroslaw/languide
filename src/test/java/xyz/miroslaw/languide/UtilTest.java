package xyz.miroslaw.languide;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class UtilTest {
    public static final LocalDate localDate = LocalDate.of(2017, 10, 22);
    public static final Date date1 = Date.valueOf(localDate);
    public static final List<String> LANG_TEXT1 = Arrays.asList("Take a one egg");
    public static final List<String> LANG_TEXT2 = Arrays.asList("Weź jedno jajko");
    public  static final Article ARTICLE = Article.builder().title("cooking")
            .firstLanguage(LANG_TEXT1)
            .secondLanguage(LANG_TEXT2)
            .tag("cooking")
            .creationDate(date1)
            .hidden(false).build();
    public  static final ArticleCommand ARTICLE_COMMAND = new ArticleCommand("cooking", "Take a one egg", "Weź jedno jajko",  "Cooking", date1, null);

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}