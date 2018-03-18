package xyz.miroslaw.languide.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(exclude="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notebook {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @NotNull
    private boolean isPublic;
    @Singular
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notebook")
    private Set<Article> articles;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

}
