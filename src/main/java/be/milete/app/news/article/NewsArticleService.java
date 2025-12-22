package be.milete.app.news.article;

import be.milete.app.exception.ResourceNotFoundException;
import be.milete.app.news.channel.NewsChannel;
import be.milete.app.news.channel.NewsChannelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.Assert.notNull;

@Service
public class NewsArticleService {

    private final NewsChannelRepository channelRepository;

    public NewsArticleService(NewsChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Transactional
    public void createNewsArticle(NewsArticleRequest request, int newsChannelId){
        notNull(request, "request must not be null");

        Optional<NewsChannel> optionalNewsChannel = channelRepository.findById(newsChannelId);
        if (optionalNewsChannel.isEmpty()){
            throw ResourceNotFoundException.fromName("newsChannel");
        }
        NewsChannel newsChannel = optionalNewsChannel.get();
        NewsArticle newsArticle = new NewsArticle(request.title(), request.paragraph());
        newsChannel.addArticle(newsArticle);
        channelRepository.save(newsChannel);
    }

}
