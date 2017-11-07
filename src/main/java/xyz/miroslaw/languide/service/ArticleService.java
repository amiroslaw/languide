package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findArticles();
    Article findById(Long id);
    void deleteById(Long id);
    void createArticle();

    void saveArticle(Article article);
}
