package xyz.miroslaw.languide.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Data
@Entity
public class Dictionary {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    @MapKeyColumn(name="ORIGINAL")
    @Column(name="TRANSLATED")
    private Map<String,String> words;
    @OneToOne
    private User user;

    Dictionary(){}
    public Dictionary(Map<String, String> words) {
        this.words = words;
    }

    public Dictionary(Map<String, String> words, User user) {
        this.words = words;
        this.user = user;
    }
}
