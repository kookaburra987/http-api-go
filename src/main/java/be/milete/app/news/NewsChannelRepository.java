package be.milete.app.news;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NewsChannelRepository extends CrudRepository<NewsChannel, Integer> {

    boolean existsByName(String name);

    @Query(value = "select (count(n) > 0) from NewsChannel n where n.id <> ?2 and n.name = ?1")
    boolean existsByNameAndIdIsNot(String name, int id);

}
