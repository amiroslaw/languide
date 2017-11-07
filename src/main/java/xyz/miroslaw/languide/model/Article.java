package xyz.miroslaw.languide.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Entity
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String firsLanguage;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String secondLanguage;
    private String tag;
    @CreatedDate
    private Date createDate;
    @ManyToOne
    private Notebook notebook;

    Article(){}

    public Article(String title, String firsLanguage, String secondLanguage, String tag, Date createDate) {
        this.title = title;
        this.firsLanguage = firsLanguage;
        this.secondLanguage = secondLanguage;
        this.tag = tag;
        this.createDate = createDate;
    }

    public Article(String title, String firsLanguage, String secondLanguage, String tag, Date createDate, Notebook notebook) {
        this(title, firsLanguage, secondLanguage, tag, createDate);
        this.notebook = notebook;
    }
}
