package xyz.miroslaw.languide.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.miroslaw.languide.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer>{
}
