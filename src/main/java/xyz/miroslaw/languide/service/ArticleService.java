package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findArticles();
    Article findById(Long id);
    void deleteById(Long id);
    void createArticle(ArticleCommand article);

    void updateArticle(Long id);
}
