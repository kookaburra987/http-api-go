package apitests.tests;

import apitests.NewsChannelTestRequest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static apitests.utils.ApiTestConstants.*;
import static apitests.utils.WebTestClientFactory.createWebTestClient;

class PutNewsChannelTest {

    private final WebTestClient webClient = createWebTestClient();

    @Test
    void returnsForbiddenIfClientHasRoleUserAndNotAdmin(){
        NewsChannelTestRequest request = new NewsChannelTestRequest("theName", "theDescription");

        Mono<NewsChannelTestRequest> requestMono = Mono.just(request);

        webClient.put()
                .uri(NEWS_CHANNEL_PATH + "/" + 1)
                .body(requestMono, NewsChannelTestRequest.class)
                .header("Authorization", TEST_USER_BASIC_AUTH)
                .exchange()
                .expectStatus()
                .isForbidden();
    }

    @Test
    void returnsUnauthorizedIfRequestWithoutAuthentication(){
        NewsChannelTestRequest request = new NewsChannelTestRequest("theName", "theDescription");

        Mono<NewsChannelTestRequest> requestMono = Mono.just(request);

        webClient.put()
                .uri(NEWS_CHANNEL_PATH + "/" + 1)
                .body(requestMono, NewsChannelTestRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody().isEmpty();
    }

    @Test
    void givenNotExistsReturnsNotFound(){
        NewsChannelTestRequest request = new NewsChannelTestRequest("theName", "theDescription");

        Mono<NewsChannelTestRequest> requestMono = Mono.just(request);

        webClient.put()
                .uri(NEWS_CHANNEL_PATH + "/" + 1)
                .body(requestMono, NewsChannelTestRequest.class)
                .header("Authorization", ADMIN_USER_BASIC_AUTH)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    //todo: add more tests here
}
