package be.milete.app.news.article;

import be.milete.app.exception.ResourceNotFoundException;
import be.milete.app.news.channel.NewsChannel;
import be.milete.app.news.channel.NewsChannelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsArticleServiceCreateNewsArticleTest {

    @InjectMocks
    private NewsArticleService service;

    @Mock
    private NewsChannelRepository channelRepository;
    @Captor
    private ArgumentCaptor<NewsArticle> articleCaptor;

    @Test
    void givenRequestNullShouldThrowIllegalArgumentException(){
        Executable doCreate = () -> service.createNewsArticle(null, 1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreate);

        assertEquals("request must not be null", exception.getMessage());
        verifyNoInteractions(channelRepository);
    }

    @Test
    void givenChannelNotFoundShouldThrowResourceNotFoundException(){
        when(channelRepository.findById(1)).thenReturn(empty());
        NewsArticleRequest request = new NewsArticleRequest("theTitle", "theParagraph");

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> service.createNewsArticle(request, 1));

        assertEquals("newsChannel not found", exception.getMessage());
    }

    @Test
    void givenRequestSavedNewsArticle(){
        NewsChannel newsChannel = mock(NewsChannel.class);
        when(channelRepository.findById(1)).thenReturn(of(newsChannel));
        NewsArticleRequest request = new NewsArticleRequest("theTitle", "theParagraph");

        service.createNewsArticle(request, 1);

        verify(newsChannel, times(1)).addArticle(articleCaptor.capture());
        verify(channelRepository, times(1)).save(newsChannel);
        NewsArticle newsArticle = articleCaptor.getValue();
        assertEquals("theTitle", newsArticle.getTitle());
        assertEquals("theParagraph", newsArticle.getParagraph());
    }


}
