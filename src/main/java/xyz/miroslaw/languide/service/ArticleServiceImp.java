package xyz.miroslaw.languide.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<Article> findPublicArticles() {
        List<Article> allArticles = (List<Article>) articleRepository.findAll();
        if (allArticles.isEmpty()) return new ArrayList<>();
        return allArticles.stream().filter(article -> !article.isHidden()).collect(Collectors.toList());
    }

    @Override
    public List<Article> findNotebookArticles(final Long id) {
        List<Article> allArticles = (ArrayList<Article>) articleRepository.findAll();
        return allArticles.stream()
                .filter(article -> article.getNotebook() != null)
                .filter(article -> article.getNotebook().getId().equals(id))
                .collect(Collectors.toList());
    }

    @Override
    public Article findById(final Long id) {
        return Optional.ofNullable(articleRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }

    @Override
    public List<Article> findArticlesByUserId(final Long id) {
        return articleRepository.findArticlesByUserId(id);
    }

    @Override
    public void deleteById(final Long id) {
        articleRepository.delete(getIfExist(id));
    }

    private Article getIfExist(final Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent()) {
            throw new NotFoundException("Not found. Id: " + id);
        }
        return article.get();
    }

    @Override
    public Article createOrUpdateArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void updateArticle(Article article, final long articleId) {
        Article oldArticle = findById(articleId);
        setValues(article, oldArticle);
        oldArticle.setCreationDate(Calendar.getInstance().getTime());
        oldArticle.setNotebook(article.getNotebook());
        createOrUpdateArticle(oldArticle);
    }

    @Override
    public void updateArticle(Article article, final long articleId, final long notebookId) {
        Article oldArticle = findById(articleId);
        setValues(article, oldArticle);
        oldArticle.setFirstLanguage(article.getFirstLanguage());
        oldArticle.setSecondLanguage(article.getSecondLanguage());
        oldArticle.setCreationDate(article.getCreationDate());
        oldArticle.setNotebook(notebookService.findById(notebookId));
        createOrUpdateArticle(oldArticle);
    }

    private void setValues(Article article, Article oldArticle) {
        oldArticle.setTitle(article.getTitle());
        oldArticle.setTag(article.getTag());
        oldArticle.setHidden(article.isHidden());
    }

}
