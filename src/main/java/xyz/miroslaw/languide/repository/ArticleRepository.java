package xyz.miroslaw.languide.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long>{
    public final static String FIND_BY_ID_STATE = "SELECT * FROM ARTICLE a WHERE a.NOTEBOOK_ID  in (SELECT n.ID FROM NOTEBOOK n WHERE USER_ID = :id)";
    @Query(value = FIND_BY_ID_STATE, nativeQuery = true)
    public List<Article> findArticlesByUserId(@Param("id") Long id);
}
