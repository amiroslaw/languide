package xyz.miroslaw.languide.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Size(min=3)
    private String password;
    private String email;
    private String role;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Notebook> notebooks;
    @OneToOne(cascade = CascadeType.ALL)
    private Dictionary dictionary;

    public User(){}
    public User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(String name, String password, String role,  Set<Notebook> notebooks, Dictionary dictionary) {
        this(name, password, role);
        this.notebooks = notebooks;
        this.dictionary = dictionary;
    }
}
