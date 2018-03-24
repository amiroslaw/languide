package xyz.miroslaw.languide.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude="dictionary")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Translation {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String source;
    private String articleTranslation;
    @ElementCollection
    private List<String> googleTranslation;
//    private List<String> cambridgeTranslation;
    private String audioURL;
    @ManyToOne
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;


}
