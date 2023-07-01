package xyz.miroslaw.languide.command;

import lombok.Data;
import xyz.miroslaw.languide.model.Notebook;

import java.util.Date;
import javax.validation.constraints.NotBlank;

@Data
public class ArticleCommand {

    private Long id;
    private String title;
    @NotBlank
    private String firstLanguage;
    @NotBlank
    private String secondLanguage;
    private String tag;
    private Date creationDate;
    private Notebook notebook;

    public ArticleCommand() {
    }

    public ArticleCommand(String title, String firstLanguage, String secondLanguage, String tag, Date creationDate,
            Notebook notebook) {
        this.title = title;
        this.firstLanguage = firstLanguage;
        this.secondLanguage = secondLanguage;
        this.tag = tag;
        this.creationDate = creationDate;
        this.notebook = notebook;
    }

}
