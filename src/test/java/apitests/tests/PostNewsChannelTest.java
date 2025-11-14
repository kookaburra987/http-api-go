package apitests.tests;


import apitests.NewsChannelTestRequest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static apitests.utils.ApiTestConstants.*;
import static apitests.utils.NewsChannelActions.createNewsChannel;
import static apitests.utils.WebTestClientFactory.createWebTestClient;

/**
 * Tests the POST /news-channel endpoint. Requires a live running http-api-go server.
 */
class PostNewsChannelTest {

    private final WebTestClient webClient = createWebTestClient();

    @Test
    void returnsForbiddenIfClientHasRoleUserAndNotAdmin(){
        NewsChannelTestRequest request = new NewsChannelTestRequest("theName", "theDescription");

        Mono<NewsChannelTestRequest> requestMono = Mono.just(request);

        webClient.post()
                .uri(NEWS_CHANNEL_PATH)
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

        webClient.post()
                .uri(NEWS_CHANNEL_PATH)
                .body(requestMono, NewsChannelTestRequest.class)
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody()
                .isEmpty();
    }

    @Test
    void returnsCreatedIfClientHasRoleAdmin(){
        String randomName = createRandomName();
        NewsChannelTestRequest request = new NewsChannelTestRequest(randomName, "theDescription");

        createNewsChannel(request);
    }

    @Test
    void returnsConflictIfNameIsAlreadyInUse(){
        String name = createRandomName();
        NewsChannelTestRequest request1 = new NewsChannelTestRequest(name, "theDescription");
        createNewsChannel(request1);

        NewsChannelTestRequest request2 = new NewsChannelTestRequest(name, "theOtherDescription");
        Mono<NewsChannelTestRequest> requestMono2 = Mono.just(request2);
        webClient.post()
                .uri(NEWS_CHANNEL_PATH)
                .body(requestMono2, NewsChannelTestRequest.class)
                .header("Authorization", ADMIN_USER_BASIC_AUTH)
                .exchange()
                .expectStatus()
                .isEqualTo(409)
                .expectBody()
                .json("{\"message\":\"name must be unique and is already in use\"}");
    }

    private String createRandomName() {
        //random name is created so that it does not conflict with data that already exists in database
        int randomInt = RandomUtils.insecure().randomInt();
        return "theName-" + randomInt;
    }

}
