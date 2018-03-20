package xyz.miroslaw.languide.util;

import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConverterUtil {

    public static Article convertToArticle(ArticleCommand command) {
        if (command == null) return null;
        Article article = new Article();
        article.setId(command.getId());
        article.setTitle(command.getTitle());
        article.setCreationDate(Calendar.getInstance().getTime());
        article.setTag(command.getTag());
        article.setNotebook(command.getNotebook());
        article.setFirstLanguage(convertToList(command.getFirstLanguage()));
        article.setSecondLanguage(convertToList(command.getSecondLanguage()));

        return article;
    }

    static List<String> convertToList(String lang) {
        return Stream.of(lang)
                .map(e -> e.split("(?<=\\.\\s)|(?<=\\?\\s)|(?<=!\\s)|(?=-)"))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }
}
