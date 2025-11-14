package apitests.utils;

/**
 * Container for constants for the api-tests
 */
public class ApiTestConstants {
    private ApiTestConstants() {
    }

    public static final String BASE_URL = "http://localhost:8080/go";

    //value for Authorization header that contains credentials of the test-user
    public static final String TEST_USER_BASIC_AUTH = "Basic dGVzdFVzZXI6dGVzdFB3ZA==";

    public static final String ADMIN_USER_BASIC_AUTH = "Basic dGVzdEFkbWluOmFkbWluUHdk";

    public static final String INFO_VERSION_EQUALS_PATH = "/info/version/equals";

    public static final String NEWS_CHANNEL_PATH = "/news-channel";
}
