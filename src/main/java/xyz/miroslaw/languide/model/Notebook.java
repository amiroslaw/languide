package xyz.miroslaw.languide.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude="user")
@Entity
public class Notebook {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @NotNull
    private boolean isPublic;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notebook")
    private Set<Article> articles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    public Notebook(){}

    public Notebook(String title, Boolean isPublic, String description) {
        this.description = description;
        this.title = title;
        this.isPublic = isPublic;
    }

    public Notebook(String title, Boolean isPublic, String description, Set<Article> articles, User user) {
        this(title, isPublic, description);
        this.articles = articles;
        this.user = user;
    }

}
