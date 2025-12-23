package be.milete.app.news.article;

import be.milete.app.exception.ResourceNotFoundException;
import be.milete.app.news.channel.NewsChannel;
import be.milete.app.news.channel.NewsChannelRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Service
public class NewsArticleService {

    private final NewsChannelRepository channelRepository;
    private final NewsArticleRepository repository;

    public NewsArticleService(NewsChannelRepository channelRepository, NewsArticleRepository newsArticleRepository) {
        this.channelRepository = channelRepository;
        this.repository = newsArticleRepository;
    }

    @Transactional
    public void createNewsArticle(NewsArticleRequest request, int newsChannelId){
        notNull(request, "request must not be null");

        NewsChannel newsChannel = getNewsChannel(newsChannelId);
        NewsArticle newsArticle = new NewsArticle(request.title(), request.paragraph());
        newsArticle.setNewsChannel(newsChannel);
        newsChannel.addArticle(newsArticle);

        repository.save(newsArticle);
    }

    public List<NewsArticleResponse> findPaginated(int newsChannelId, int page, int size) {
        isTrue(page > -1, "page must be greater than -1");
        isTrue(size > 0, "size must be positive number");
        isTrue(size < 51, "size must be lower than 51");

        getNewsChannel(newsChannelId);
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("title"));

        List<NewsArticle> articles = repository.findAllByNewsChannelId(newsChannelId, pageRequest);
        return articles.stream()
                .map(a -> new NewsArticleResponse(a.getId(), a.getTitle(), a.getParagraph()))
                .toList();
    }

    /**
     * Retrieves the NewsChannel or throws ResourceNotFoundException.
     * @param newsChannelId unique id
     * @throws {@link ResourceNotFoundException} if the NewsChannel is not found.
     * @return {@link NewsChannel} with given id
     */
    private NewsChannel getNewsChannel(int newsChannelId) {
        Optional<NewsChannel> optionalNewsChannel = channelRepository.findById(newsChannelId);
        if (optionalNewsChannel.isEmpty()){
            throw ResourceNotFoundException.fromName("newsChannel");
        }
        return optionalNewsChannel.get();
    }
}
