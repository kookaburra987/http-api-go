package be.milete.app.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"be.milete.app.news"})
public class PersistenceConfig {

}
