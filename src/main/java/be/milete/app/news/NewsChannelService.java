package be.milete.app.news;

import be.milete.app.exception.NotUniqueValueException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
}
