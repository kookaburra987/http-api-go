package be.milete.app.news.channel;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;

import static jakarta.persistence.GenerationType.AUTO;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.length;
import static org.springframework.util.Assert.isTrue;

/**
 * A container for News so that all news can be grouped by topic or type of intended audience.
 */
@Entity
@Getter
public class NewsChannel {

    @Id
    @GeneratedValue(strategy = AUTO, generator = "news_channel_seq_gen")
    @SequenceGenerator(name = "news_channel_seq_gen", sequenceName = "news_channel_seq", allocationSize = 1)
    private Integer id;

    private String name;

    private String description;

    public NewsChannel() {
    }

    /**
     * Constructor to create a new NewsChannel
     * @param name must not be blank and must be max 20 characters
     * @param description must not be blank and must be max 500 characters
     */
    public NewsChannel(String name, String description) {
        validateName(name);
        validateDescription(description);

        this.name = name.trim();
        this.description = description.trim();
    }

    public void setName(String name) {
        validateName(name);

        this.name = name.trim();
    }

    public void setDescription(String description) {
        validateDescription(description);

        this.description = description.trim();
    }

    private void validateDescription(String description) {
        isTrue(isNotBlank(description), "description must not be blank");
        isTrue(length(description) <= 500, "description must be max 500 characters");
    }

    private void validateName(String name) {
        isTrue(isNotBlank(name), "name must not be blank");
        isTrue(length(name) <= 20, "name must be max 20 characters");
    }
}
