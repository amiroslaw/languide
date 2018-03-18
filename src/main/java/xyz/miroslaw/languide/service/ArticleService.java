package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findArticles();
    Article findById(Long id);
    List<Article> findArticlesByUserId(Long id);
    void deleteById(Long id);
    void updateArticle(Long id);
    Article createArticle(Article article);

    void updateArticleDescription(Article article, long articleId);
}
