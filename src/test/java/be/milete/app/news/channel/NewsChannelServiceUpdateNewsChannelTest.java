package be.milete.app.news.channel;

import be.milete.app.exception.NotUniqueValueException;
import be.milete.app.exception.ResourceNotFoundException;
import be.milete.app.news.channel.NewsChannel;
import be.milete.app.news.channel.NewsChannelRepository;
import be.milete.app.news.channel.NewsChannelRequest;
import be.milete.app.news.channel.NewsChannelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsChannelServiceUpdateNewsChannelTest {

    @InjectMocks
    private NewsChannelService service;

    @Mock
    private NewsChannelRepository repository;

    @Test
    void givenRequestNullShouldThrowIllegalArgumentException(){
        Executable doUpdate = () -> service.updateNewsChannel(1, null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doUpdate);
        assertEquals("request must not be null", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void givenNameAndDescriptionShouldUpdateNewsChannel(){
        NewsChannelRequest request = new NewsChannelRequest("newName", "newDescription");
        NewsChannel newsChannel = mock(NewsChannel.class);
        when(repository.findById(1)).thenReturn(Optional.of(newsChannel));

        service.updateNewsChannel(1, request);

        verify(newsChannel, times(1)).setName("newName");
        verify(newsChannel, times(1)).setDescription("newDescription");
        verify(repository, times(1)).save(newsChannel);
    }

    @Test
    void givenNewsChannelNotFoundShouldThrowResourceNotFoundException(){
        NewsChannelRequest request = new NewsChannelRequest("newName", "newDescription");
        when(repository.findById(1)).thenReturn(Optional.empty());

        Executable doUpdate = () -> service.updateNewsChannel(1, request);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, doUpdate);
        assertEquals("newsChannel not found", exception.getMessage());
    }

    @Test
    void givenNameNotUniqueShouldThrowNotUniqueValueException(){
        NewsChannelRequest request = new NewsChannelRequest("newName", "newDescription");
        when(repository.existsByNameAndIdIsNot("newName", 1)).thenReturn(true);

        Executable doUpdate = () -> service.updateNewsChannel(1, request);

        NotUniqueValueException exception = assertThrows(NotUniqueValueException.class, doUpdate);
        assertEquals("name must be unique and is already in use", exception.getMessage());
        verify(repository, times(0)).save(any());
    }

    @Test
    void shouldTrimNameAndDescriptionUpdateNewsChannel(){
        NewsChannelRequest request = new NewsChannelRequest(" newName\t", "\nnewDescription \t");
        NewsChannel newsChannel = mock(NewsChannel.class);
        when(repository.findById(1)).thenReturn(Optional.of(newsChannel));

        service.updateNewsChannel(1, request);

        verify(newsChannel, times(1)).setName("newName");
        verify(newsChannel, times(1)).setDescription("newDescription");
        verify(repository, times(1)).save(newsChannel);
    }
}
