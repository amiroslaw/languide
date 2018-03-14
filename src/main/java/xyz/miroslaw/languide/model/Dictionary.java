package xyz.miroslaw.languide.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(exclude="user")
@Entity
public class Dictionary {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private boolean isPublic;
    @ElementCollection
    @MapKeyColumn(name = "ORIGINAL")
    @Column(name = "TRANSLATED")
    private Map<String, String> words;
    @OneToOne
    private User user;

    Dictionary() {
    }
    //todo add name
    public Dictionary(Map<String, String> words, boolean isPublic, User user) {
        this.words = words;
        this.isPublic = isPublic;
        this.user = user;
    }

}
