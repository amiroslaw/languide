package xyz.miroslaw.languide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dictionary {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private boolean hidden;
    @JsonIgnore
    @OneToMany(mappedBy = "dictionary")
    private List<Translation> translations;
    @OneToOne
    private User user;
}
