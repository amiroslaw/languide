package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

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

}
