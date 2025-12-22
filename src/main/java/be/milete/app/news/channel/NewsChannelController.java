package be.milete.app.news.channel;

import be.milete.app.news.article.NewsArticleRequest;
import be.milete.app.news.article.NewsArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("news-channel")
@SecurityRequirement(name = "basicauth")
public class NewsChannelController {

    private final NewsChannelService service;

    private final NewsArticleService articleService;

    public NewsChannelController(NewsChannelService service, NewsArticleService articleService) {
        this.service = service;
        this.articleService = articleService;
    }

    @ResponseStatus(CREATED)
    @PostMapping
    public void create(@RequestBody @Validated NewsChannelRequest request){
        service.createNewsChannel(request);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(path = "/{id}")
    public void update(@PathVariable("id") Integer id, @RequestBody @Validated NewsChannelRequest request){
        service.updateNewsChannel(id, request);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") Integer id){
        service.deleteNewsChannel(id);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<NewsChannelResponse> getAll(){
        return service.getAllNewsChannels();
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/{id}/article")
    public void addArticleToChannel(@PathVariable("id") int newsChannelId, @RequestBody @Validated NewsArticleRequest request){
        articleService.createNewsArticle(request, newsChannelId);
    }
}
