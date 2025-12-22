package be.milete.app.news.channel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewsChannelRequest(
        @NotBlank @Size(max = 20) String name,
        @NotBlank @Size(max = 500) String description
) {}
