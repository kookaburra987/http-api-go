package apitests;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Tests the GET /info/version endpoint. Requires a live running http-api-go server.
 */
class GetInfoVersionTest {

    private final WebTestClient webClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:8080/go")
            .build();

    @Test
    void returnsOkAndVersion(){
        webClient.get()
                .uri("/info/version")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(String.class)
                .isEqualTo("0.0.1");
    }
}
