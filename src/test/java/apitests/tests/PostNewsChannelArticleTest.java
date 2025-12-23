package apitests.tests;

import apitests.NewsArticleTestRequest;
import apitests.NewsChannelTestRequest;
import apitests.utils.NewsChannelActions;
import org.junit.jupiter.api.Test;

import static apitests.utils.ApiTestUtils.createRandomName;
import static apitests.utils.NewsChannelActions.createNewsChannel;

class PostNewsChannelArticleTest {

    @Test
    void returnsCreatedIfClientHasRoleAdmin(){
        String randomName = createRandomName();
        NewsChannelTestRequest request = new NewsChannelTestRequest(randomName, "theDescription");
        createNewsChannel(request);

        String title = createRandomName();
        NewsArticleTestRequest testRequest = new NewsArticleTestRequest(title, "paragraph-for-" + title);
        NewsChannelActions.createNewsArticle(testRequest, 1);
    }
}
