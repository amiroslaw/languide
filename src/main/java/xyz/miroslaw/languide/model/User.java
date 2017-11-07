package xyz.miroslaw.languide.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Notebook> notebooks;
    @OneToOne(cascade = CascadeType.ALL)
    private Dictionary dictionary;

    User(){}
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, Set<Notebook> notebooks, Dictionary dictionary) {
        this(name, password);
        this.notebooks = notebooks;
        this.dictionary = dictionary;
    }
}
