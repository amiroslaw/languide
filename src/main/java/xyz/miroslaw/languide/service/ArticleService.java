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
    void updateArticle(Article article, long articleId);
    void updateArticle(Article article, long articleId, long notebookId);
}
