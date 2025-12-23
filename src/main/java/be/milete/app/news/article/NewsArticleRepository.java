package be.milete.app.news.article;


import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsArticleRepository extends CrudRepository<NewsArticle, Integer> {

    List<NewsArticle> findAllByNewsChannelId(int channelId, Pageable pageable);

}
