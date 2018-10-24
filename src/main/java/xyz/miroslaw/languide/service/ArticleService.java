package xyz.miroslaw.languide.service;

import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAllPublicArticles();
    List<Article> findAllArticlesByNotebookId(Long id);
    Article findById(Long id);
    List<Article> findArticlesByUserId(Long id);
    void deleteById(Long id);
    Article createArticle(Article article);
    void updateArticle(Article article, long articleId);
    void updateArticle(Article article, long articleId, long notebookId);
}
