package apitests;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static apitests.ApiTestConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Tests the POST /info/version/equals endpoint. Requires a live running http-api-go server.
 */
class PostInfoVersionEqualsTest {


    private final WebTestClient webClient = WebTestClient.bindToServer()
            .baseUrl(BASE_URL)
            .build();

    @Test
    void returnsTrueIfVersionEquals(){
        Mono<String> stringMono = Mono.just("0.0.1");

        webClient.post()
                .uri(INFO_VERSION_EQUALS_PATH)
                .body(stringMono, String.class)
                .header("Authorization", TEST_USER_BASIC_AUTH)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Boolean.class)
                .isEqualTo(TRUE);
    }

    @Test
    void returnsFalseIfVersionNotEquals(){
        Mono<String> stringMono = Mono.just("0.0.2");

        webClient.post()
                .uri(INFO_VERSION_EQUALS_PATH)
                .body(stringMono, String.class)
                .header("Authorization", TEST_USER_BASIC_AUTH)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Boolean.class)
                .isEqualTo(FALSE);
    }

    @Test
    void ReturnsBadRequestIfRequestWithoutBody(){
        Mono<String> stringMono = Mono.empty();

        webClient.post()
                .uri(INFO_VERSION_EQUALS_PATH)
                .body(stringMono, String.class)
                .header("Authorization", TEST_USER_BASIC_AUTH)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody()
                .json("{\"message\": \"HTTP message not readable\"}");
    }

    @Test
    void ReturnsNotAuthorizedIfRequestWithoutAuthentication(){
        Mono<String> stringMono = Mono.empty();

        webClient.post()
                .uri(INFO_VERSION_EQUALS_PATH)
                .body(stringMono, String.class)
                .exchange()
                .expectStatus()
                .isUnauthorized()
                .expectBody()
                .isEmpty();
    }
}
