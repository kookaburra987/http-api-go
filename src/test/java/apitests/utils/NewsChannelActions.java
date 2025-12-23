package apitests.utils;

import apitests.NewsArticleTestRequest;
import apitests.NewsChannelTestRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static apitests.utils.ApiTestConstants.*;
import static apitests.utils.ApiTestUtils.createRandomName;
import static apitests.utils.WebTestClientFactory.createWebTestClient;

public class NewsChannelActions {
    private NewsChannelActions() {
    }

    /**
     * Creates a news-channel by doing an HTTP-call with credentials of the ADMIN user.
     */
    public static void createNewsChannel(){
        String randomName = createRandomName();
        NewsChannelTestRequest request = new NewsChannelTestRequest(randomName, "theDescription");

        createNewsChannel(request);
    }

    /**
     * Creates a news-channel by doing an HTTP-call with credentials of the ADMIN user.
     * @param requestBody to use as body of the HTTP-request
     */
    public static void createNewsChannel(NewsChannelTestRequest requestBody){
        Mono<NewsChannelTestRequest> requestMono = Mono.just(requestBody);

        WebTestClient webClient = createWebTestClient();
        webClient.post()
                .uri(NEWS_CHANNEL_PATH)
                .body(requestMono, NewsChannelTestRequest.class)
                .header("Authorization", ADMIN_USER_BASIC_AUTH)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }

    public static void createNewsArticle(NewsArticleTestRequest requestBody, int channelId){
        Mono<NewsArticleTestRequest> requestMono = Mono.just(requestBody);

        WebTestClient webClient = createWebTestClient();
        webClient.post()
                .uri(NEWS_CHANNEL_ARTICLE_PATH.formatted(channelId))
                .body(requestMono, NewsArticleTestRequest.class)
                .header("Authorization", ADMIN_USER_BASIC_AUTH)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();
    }
}
