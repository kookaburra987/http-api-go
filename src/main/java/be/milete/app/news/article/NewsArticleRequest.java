package be.milete.app.news.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewsArticleRequest(
        @NotBlank @Size(max = 40) String title,
        @NotBlank @Size(max = 500) String paragraph
) {
}
