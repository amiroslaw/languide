package xyz.miroslaw.languide.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.miroslaw.languide.model.Notebook;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
public class ArticleCommand {
    private Long id;
    private String title;
    @NotBlank
    private String firstLanguage;
    @NotBlank
    private String secondLanguage;
    private String tag;
    private Date createDate;
    private Notebook notebook;

    public ArticleCommand(String title, @NotBlank String firstLanguage, @NotBlank String secondLanguage, String tag, Date createDate, Notebook notebook) {
        this.title = title;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.tag = tag;
        this.createDate = createDate;
        this.notebook = notebook;
    }
}
