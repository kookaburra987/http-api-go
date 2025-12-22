package be.milete.app.news.channel;

import be.milete.app.exception.NotUniqueValueException;
import be.milete.app.news.channel.NewsChannel;
import be.milete.app.news.channel.NewsChannelRepository;
import be.milete.app.news.channel.NewsChannelRequest;
import be.milete.app.news.channel.NewsChannelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsChannelServiceCreateNewsChannelTest {

    @InjectMocks
    private NewsChannelService service;

    @Mock
    private NewsChannelRepository repository;

    @Captor
    private ArgumentCaptor<NewsChannel> newsChannelCaptor;

    @Test
    void givenNullThrowsIllegalArgumentException(){
        Executable doCreateNewsChannel = () -> service.createNewsChannel(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doCreateNewsChannel);
        assertEquals("request must not be null", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void givenNameAlreadyInUseThrowsNotUniqueValueException(){
        NewsChannelRequest request = new NewsChannelRequest("theName", "theDescription");
        when(repository.existsByName("theName")).thenReturn(true);

        Executable doCreateNewsChannel = () -> service.createNewsChannel(request);

        NotUniqueValueException exception = assertThrows(NotUniqueValueException.class, doCreateNewsChannel);
        assertEquals("name must be unique and is already in use", exception.getMessage());
        verify(repository, times(0)).save(any());
    }

    @Test
    void givenRequestSavesNewsChannel(){
        NewsChannelRequest request = new NewsChannelRequest("theName", "theDescription");

        service.createNewsChannel(request);

        verify(repository, times(1)).save(newsChannelCaptor.capture());
        NewsChannel newsChannel = newsChannelCaptor.getValue();
        assertEquals("theName", newsChannel.getName());
        assertEquals("theDescription", newsChannel.getDescription());
    }
}
