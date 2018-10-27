package xyz.miroslaw.languide.util;

import org.junit.Test;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.model.Notebook;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


public class ConverterUtilTest {

    @Test
    public void convertToArticle_shouldConvertCorrectly() throws Exception {
        final String LANGUAGE_TEXT = "Aliquam a dignissim urna -at lacinia turpis. Aliquam erat volutpat? Vivamus tempor magna vel libero aliquet? Suscipit semper at lacinia a.";
        final List<String> SPLIT_TEXT = Arrays.asList(
                "Aliquam a dignissim urna ",
                "-at lacinia turpis. ",
                "Aliquam erat volutpat? ",
                "Vivamus tempor magna vel libero aliquet? ",
                "Suscipit semper at lacinia a.");
        final String LANGUAGE_TEXT2 = "Aliquam a dignissim urna -at lacinia turpis.";
        final List<String> SPLIT_TEXT2 = Arrays.asList(
                "Aliquam a dignissim urna ",
                "-at lacinia turpis. ");

        final ArticleCommand articleCommand = new ArticleCommand("title", LANGUAGE_TEXT, LANGUAGE_TEXT2, "tag", null, new Notebook());

        Article result = ConverterUtil.convertToArticle(articleCommand);

        assertThat(result.getFirstLanguage()).isNotEmpty();
        assertThat(result).isEqualToComparingOnlyGivenFields(SPLIT_TEXT);
        assertThat(result).isEqualToComparingOnlyGivenFields(SPLIT_TEXT2);

    }

    @Test
    public void convertToArticle_shouldReturnEmptyArticle() {
        Article article = ConverterUtil.convertToArticle(null);

        assertThat(article).isEqualTo(new Article());
    }
}