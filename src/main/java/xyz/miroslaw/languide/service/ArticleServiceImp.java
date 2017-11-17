package xyz.miroslaw.languide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.miroslaw.languide.command.ArticleCommand;
import xyz.miroslaw.languide.exception.NotFoundException;
import xyz.miroslaw.languide.model.Article;
import xyz.miroslaw.languide.repository.ArticleRepository;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void deleteById(Long id) {
        articleRepository.delete(getIfExist(id));
    }

    @Override
    public void updateArticle(Long id) {
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
    public Article createArticle(ArticleCommand command) {
        //will I have notebook object?
        Article article = convertToArticle(command);
        return articleRepository.save(article);
    }

    private Article convertToArticle(ArticleCommand command) {
        if (command == null) return null;
        Article article = new Article();
        article.setId(command.getId());
        article.setTitle(command.getTitle());
        article.setCreationDate(command.getCreationDate());
        article.setTag(command.getTag());
        article.setNotebook(command.getNotebook());
        article.setFirstLanguage(convertToList(command.getFirstLanguage()));
        article.setSecondLanguage(convertToList(command.getSecondLanguage()));

        return article;
    }

    List<String> convertToList(@NotBlank String lang) {
        return Stream.of(lang)
                .map(e -> e.split("(?<=\\.\\s)|(?<=\\?\\s)|(?<=!\\s)|(?=-)"))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }
}
