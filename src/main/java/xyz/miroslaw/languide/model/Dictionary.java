package xyz.miroslaw.languide.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
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

    //todo add name
    Dictionary() {
    }

    public Dictionary(Map<String, String> words, boolean isPublic, User user) {
        this.words = words;
        this.isPublic = isPublic;
        this.user = user;
    }

}
