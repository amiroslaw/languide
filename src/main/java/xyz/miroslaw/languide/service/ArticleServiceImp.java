package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp implements ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findPublicArticles() {
        List<Article> allArticles = (List<Article>) articleRepository.findAll();
        return allArticles.stream().filter(article -> !article.isHidden()).collect(Collectors.toList());
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

    @Override
    public void updateArticle(Long id) {
        //todo change cos no sense
        articleRepository.save(getIfExist(id));
    }

    private Article getIfExist(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        if (!article.isPresent()) {
            throw new NotFoundException("Not found. Id: " + id);
        }
        return article.get();
    }

    @Override
    public Article createArticle(Article article) {
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
        createArticle(oldArticle);
    }

}
