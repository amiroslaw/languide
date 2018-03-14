package xyz.miroslaw.languide.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode(exclude="notebook")
@Entity
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ElementCollection
    private List<String> firstLanguage;
    @ElementCollection
    private List<String> secondLanguage;
    private String tag;
    @CreatedDate
    private Date creationDate;
    @ManyToOne
    private Notebook notebook;

    public Article(){}

    public Article(String title, List<String> firstLanguage, List<String> secondLanguage, String tag, Date creationDate) {
        this.title = title;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.tag = tag;
        this.creationDate = creationDate;
    }

    public Article(String title, List<String> firstLanguage, List<String> secondLanguage, String tag, Date creationDate, Notebook notebook) {
        this(title, firstLanguage, secondLanguage, tag, creationDate);
        this.notebook = notebook;
    }
}
