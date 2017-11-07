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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notebook")
    private Set<Article> articles;
    @ManyToOne
    private User user;

    public Notebook(String description) {
        this.description = description;
    }

    public Notebook(String description, Set<Article> articles) {
        this.description = description;
        this.articles = articles;
    }
}
