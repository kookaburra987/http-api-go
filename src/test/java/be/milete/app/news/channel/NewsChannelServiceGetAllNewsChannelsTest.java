package be.milete.app.news.channel;

import be.milete.app.news.channel.NewsChannel;
import be.milete.app.news.channel.NewsChannelRepository;
import be.milete.app.news.channel.NewsChannelResponse;
import be.milete.app.news.channel.NewsChannelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewsChannelServiceGetAllNewsChannelsTest {

    @InjectMocks
    private NewsChannelService service;

    @Mock
    private NewsChannelRepository repository;

    @Test
    void returnsListOfNewsChannelResponses(){
        NewsChannel newsChannel1 = mockNewsChannel(1,"name1");
        NewsChannel newsChannel2 = mockNewsChannel(2,"name2");
        when(repository.findAll()).thenReturn(asList(newsChannel1, newsChannel2));

        List<NewsChannelResponse> allNewsChannels = service.getAllNewsChannels();

        assertEquals(2, allNewsChannels.size());
        assertTrue(containsNewsChannel(1, "name1", allNewsChannels));
        assertTrue(containsNewsChannel(2, "name2", allNewsChannels));
    }

    private NewsChannel mockNewsChannel(int id, String name){
        NewsChannel mock = mock(NewsChannel.class);
        when(mock.getId()).thenReturn(id);
        when(mock.getName()).thenReturn(name);

        return mock;
    }

    private boolean containsNewsChannel(int id, String name, List<NewsChannelResponse> newsChannels){
        return newsChannels.stream()
                .anyMatch(r -> r.id() == id && name.equals(r.name()));
    }

}
