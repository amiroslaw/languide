package xyz.miroslaw.languide.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Notebook> notebooks;
    @OneToOne(cascade = CascadeType.ALL)
    private Dictionary dictionary;

    public User(){}
    public User(String name, String password, Collection<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String password, String role, Set<Notebook> notebooks, Dictionary dictionary, Collection<Role> roles) {
        this(name, password, roles);
        this.notebooks = notebooks;
        this.dictionary = dictionary;
    }
}
