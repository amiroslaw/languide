package xyz.miroslaw.languide.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class Notebook {

    @Id
    private Long id;
    @NotNull
    private String title;
    private String description;
    @NotNull
    private boolean isPublic;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notebook")
    private Set<Article> articles;
    @ManyToOne
    private User user;

    // i don't know if empty constructor is needed
    public Notebook(String title, Boolean isPublic) {
        this.title = title;
        this.isPublic = isPublic;
    }

    public Notebook(String title, Boolean isPublic, String description, Set<Article> articles) {
        this(title, isPublic);
        this.description = description;
        this.articles = articles;
    }
}
