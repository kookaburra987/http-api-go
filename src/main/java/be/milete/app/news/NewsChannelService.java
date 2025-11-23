package be.milete.app.news;

import be.milete.app.exception.NotUniqueValueException;
import be.milete.app.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Service
public class NewsChannelService {

    private final NewsChannelRepository repository;

    public NewsChannelService(NewsChannelRepository repository) {
        this.repository = repository;
    }

    /*
    Method is synchronized to prevent Race condition when two threads pass the existsByName validation at the same time.
    */
    @Transactional
    public synchronized void createNewsChannel(NewsChannelRequest request) {
        notNull(request, "request must not be null");

        String name = request.name().trim();

        if (repository.existsByName(name)) {
            throw new NotUniqueValueException("name must be unique and is already in use");
        }

        NewsChannel newsChannel = new NewsChannel(name, request.description());
        repository.save(newsChannel);
    }

    @Transactional
    public List<NewsChannelResponse> getAllNewsChannels() {
        Iterable<NewsChannel> allNewsChannels = repository.findAll();
        List<NewsChannelResponse> responseList = new ArrayList<>();
        allNewsChannels.forEach(newsChannel -> {
            NewsChannelResponse resp = new NewsChannelResponse(newsChannel.getId(), newsChannel.getName());
            responseList.add(resp);
        });
        return responseList;
    }

    public void updateNewsChannel(int id, NewsChannelRequest request) {
        notNull(request, "request must not be null");

        String newName = request.name().trim();
        String newDescription = request.description().trim();

        if (repository.existsByNameAndIdIsNot(newName, id)){
            throw new NotUniqueValueException("name must be unique and is already in use");
        }

        Optional<NewsChannel> optionalNewsChannel = repository.findById(id);
        if (optionalNewsChannel.isEmpty()){
            throw new ResourceNotFoundException("newsChannel not found");
        }

        NewsChannel newsChannel = optionalNewsChannel.get();
        newsChannel.setName(newName);
        newsChannel.setDescription(newDescription);

        repository.save(newsChannel);
    }
}
