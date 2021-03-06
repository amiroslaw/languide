package xyz.miroslaw.languide.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import java.util.*;

@Slf4j
@Service
public class ArticleServiceImp implements ArticleService {

    private ArticleRepository articleRepository;
    private NotebookService notebookService;

    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository, NotebookService notebookService) {
        this.notebookService = notebookService;
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findAllPublicArticles() {
        return articleRepository.findAllByHiddenFalse();
    }

    @Override
    public List<Article> findAllArticlesByNotebookId(final Long notebookId) {
        return articleRepository.findAllByNotebookId(notebookId);
    }

    @Override
    public Article findById(final Long id) {
        return Optional.ofNullable(articleRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public List<Article> findArticlesByUserId(final Long UserId) {
        return articleRepository.findArticlesByUserId(UserId);
    }

    @Override
    public void deleteById(final Long id) {
        articleRepository.delete(getIfExist(id));
    }

    private Article getIfExist(final Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (!article.isPresent()) {
            throw new NotFoundException("Not found. Id: " + articleId);
        }
        return article.get();
    }

    @Override
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void updateArticle(Article article, final long articleId) {
        Article oldArticle = findById(articleId);
        setValues(article, oldArticle);
        oldArticle.setCreationDate(Calendar.getInstance().getTime());
        oldArticle.setNotebook(article.getNotebook());
        createArticle(oldArticle);
    }

    @Override
    public void updateArticle(Article article, final long articleId, final long notebookId) {
        Article oldArticle = findById(articleId);
        setValues(article, oldArticle);
        oldArticle.setFirstLanguage(article.getFirstLanguage());
        oldArticle.setSecondLanguage(article.getSecondLanguage());
        oldArticle.setCreationDate(article.getCreationDate());
        oldArticle.setNotebook(notebookService.findById(notebookId));
        createArticle(oldArticle);
    }

    private void setValues(Article article, Article oldArticle) {
        oldArticle.setTitle(article.getTitle());
        oldArticle.setTag(article.getTag());
        oldArticle.setHidden(article.isHidden());
    }

}
