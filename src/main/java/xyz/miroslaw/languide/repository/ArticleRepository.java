package xyz.miroslaw.languide.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import xyz.miroslaw.languide.model.Article;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    String FIND_BY_USERID = "SELECT * FROM ARTICLE a WHERE a.NOTEBOOK_ID  in (SELECT n.ID FROM NOTEBOOK n WHERE USER_ID = :id)";

    @Query(value = FIND_BY_USERID, nativeQuery = true)
    List<Article> findArticlesByUserId(@Param("id") Long id);

    String FIND_BY_NOTEBOOK_ID = "SELECT * FROM ARTICLE WHERE NOTEBOOK_ID  = :id";
//    String FIND_BY_NOTEBOOK_ID = "SELECT * FROM ARTICLE a INNER JOIN NOTEBOOK n ON a.NOTEBOOK_ID=n.ID WHERE n.ID  = :id";
    @Query(value = FIND_BY_NOTEBOOK_ID, nativeQuery = true)
    List<Article> findAllByNotebookId(@Param("id") Long id);

    List<Article> findAllByHiddenFalse();
}
