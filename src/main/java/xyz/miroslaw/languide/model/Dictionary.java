package xyz.miroslaw.languide.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(exclude="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Singular
    private Map<String, String> words;
    @OneToOne
    private User user;


}
