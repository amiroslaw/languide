package xyz.miroslaw.languide.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;
import xyz.miroslaw.languide.repository.NotebookRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
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
        return allArticles.stream().filter(article -> !article.isHidden()).collect(Collectors.toList());
    }
    @Override
    public List<Article> findNotebookArticles(final Long id) {
        List<Article> allArticles = (List<Article>) articleRepository.findAll();
        return allArticles.stream().filter(article -> article.getNotebook().getId() == id).collect(Collectors.toList());
    }

    @Override
    public Article findById(Long id) {
        return Optional.ofNullable(articleRepository.findById(id))
                .map(Optional::get)
                .orElseThrow(() -> new NotFoundException("Not found. Id: " + id));
    }
    @Override
    public List<Article> findArticlesByUserId(Long id){
        return articleRepository.findArticlesByUserId(id);
    }

    @Override
    public void deleteById(Long id) {
        articleRepository.delete(getIfExist(id));
    }

    private Article getIfExist(Long id) {
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
    public void updateArticleDescription(Article article, long articleId) {
        Article oldArticle = findById(articleId);
        oldArticle.setTitle(article.getTitle());
        oldArticle.setTag(article.getTag());
        oldArticle.setHidden(article.isHidden());
        oldArticle.setCreationDate(Calendar.getInstance().getTime());
        oldArticle.setNotebook(article.getNotebook());
       createOrUpdateArticle(oldArticle);
    }
@Override
    public void updateArticle(Article article, long articleId, long notebookId) {
        Article oldArticle = findById(articleId);
        oldArticle.setTitle(article.getTitle());
        oldArticle.setTag(article.getTag());
        oldArticle.setHidden(article.isHidden());
        oldArticle.setFirstLanguage(article.getFirstLanguage());
        oldArticle.setSecondLanguage(article.getSecondLanguage());
        oldArticle.setCreationDate(article.getCreationDate());
        oldArticle.setNotebook(notebookService.findById(notebookId));
       createOrUpdateArticle(oldArticle);
    }

}
