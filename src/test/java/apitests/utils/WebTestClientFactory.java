package apitests.utils;

import org.springframework.test.web.reactive.server.WebTestClient;

import static apitests.utils.ApiTestConstants.BASE_URL;

/**
 * Static factory class to create the WebTestClient to test the http-api-go application.
 */
public class WebTestClientFactory {
    private WebTestClientFactory() {
    }

    public static WebTestClient createWebTestClient() {
        return WebTestClient.bindToServer()
                .baseUrl(BASE_URL)
                .build();
    }
}
