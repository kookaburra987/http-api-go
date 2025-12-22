package be.milete.app.news.channel;

import be.milete.app.exception.ResourceNotFoundException;
import be.milete.app.news.channel.NewsChannelRepository;
import be.milete.app.news.channel.NewsChannelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsChannelServiceDeleteNewsChannelTest {

    @InjectMocks
    private NewsChannelService service;

    @Mock
    private NewsChannelRepository repository;

    @Test
    void givenIdNullThrowsIllegalArgumentException(){
        Executable doDelete = () -> service.deleteNewsChannel(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, doDelete);

        assertEquals("id must not be null", exception.getMessage());
        verifyNoInteractions(repository);
    }

    @Test
    void givenNewsChannelNotExistsThrowsResourceNotFoundException(){
        Executable doDelete = () -> service.deleteNewsChannel(1);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, doDelete);
        assertEquals("newsChannel not found", exception.getMessage());
    }

    @Test
    void givenNewsChannelFoundDeletesIt(){
        when(repository.existsById(1)).thenReturn(true);

        service.deleteNewsChannel(1);

        verify(repository, times(1)).deleteById(1);
    }
}
