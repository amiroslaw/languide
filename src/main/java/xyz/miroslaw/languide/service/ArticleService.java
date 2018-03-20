package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findPublicArticles();
    List<Article> findNotebookArticles(Long id);
    Article findById(Long id);
    List<Article> findArticlesByUserId(Long id);
    void deleteById(Long id);
    Article createOrUpdateArticle(Article article);
    void updateArticleDescription(Article article, long articleId);
}
