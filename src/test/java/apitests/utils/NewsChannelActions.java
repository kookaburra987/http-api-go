package apitests.utils;

import apitests.NewsChannelTestRequest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static apitests.utils.ApiTestConstants.ADMIN_USER_BASIC_AUTH;
import static apitests.utils.ApiTestConstants.NEWS_CHANNEL_PATH;
import static apitests.utils.WebTestClientFactory.createWebTestClient;

public class NewsChannelActions {
    private NewsChannelActions() {
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
                .expectStatus()
                .isCreated()
                .expectBody()
                .isEmpty();
    }
}
