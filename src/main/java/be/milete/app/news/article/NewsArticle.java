package be.milete.app.news.article;

import be.milete.app.news.channel.NewsChannel;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.AUTO;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.length;
import static org.springframework.util.Assert.isTrue;

/**
 * Represents an article on a {@link be.milete.app.news.channel.NewsChannel} that contains a title and paragraph.
 */
@Entity
@Getter
public class NewsArticle {

    public NewsArticle() {
    }

    @Id
    @GeneratedValue(strategy = AUTO, generator = "news_article_seq_gen")
    @SequenceGenerator(name = "news_article_seq_gen", sequenceName = "news_article_seq", allocationSize = 1)
    private Integer id;

    private String title;

    private String paragraph;

    @ManyToOne
    private NewsChannel newsChannel;

    public NewsArticle(String title, String paragraph) {
        validateTitle(title);
        validateParagraph(paragraph);

        this.title = title;
        this.paragraph = paragraph;
    }

    private void validateTitle(String title){
        isTrue(isNotBlank(title), "title must not be blank");
        isTrue(length(title) <= 40, "title must be max 40 characters");
    }

    private void validateParagraph(String text){
        isTrue(isNotBlank(text), "paragraph must not be blank");
        isTrue(length(text) <= 500, "paragraph must be max 500 characters");
    }
}
