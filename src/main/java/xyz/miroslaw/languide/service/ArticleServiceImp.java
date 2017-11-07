package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImp(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public List<Article> findArticles() {
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public Article findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void createArticle() {

    }

    @Override
    public void saveArticle(Article article) {

    }
}
