package xyz.miroslaw.languide.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude="notebook")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Singular("first")
    @Column(columnDefinition = "TEXT")
    @ElementCollection
    private List<String> firstLanguage;
    @Singular("second")
    @Column(columnDefinition = "TEXT")
    @ElementCollection
    private List<String> secondLanguage;
    private String tag;
    @CreatedDate
    private Date creationDate;
    private boolean hidden;
    @JsonIgnore
    @ManyToOne
    private Notebook notebook;

}
