package xyz.miroslaw.languide.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Data
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
    private Date createDate;
    @ManyToOne
    private Notebook notebook;

    public Article(){}

    public Article(String title, List<String> firstLanguage, List<String> secondLanguage, String tag, Date createDate) {
        this.title = title;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.tag = tag;
        this.createDate = createDate;
    }

    public Article(String title, List<String> firstLanguage, List<String> secondLanguage, String tag, Date createDate, Notebook notebook) {
        this(title, firstLanguage, secondLanguage, tag, createDate);
        this.notebook = notebook;
    }
}
