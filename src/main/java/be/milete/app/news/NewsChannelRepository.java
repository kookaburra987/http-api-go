package be.milete.app.news;

import org.springframework.data.repository.CrudRepository;

public interface NewsChannelRepository extends CrudRepository<NewsChannel, Integer> {

    boolean existsByName(String name);

}
