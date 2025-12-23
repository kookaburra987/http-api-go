package be.milete.app.news.channel;

import be.milete.app.news.article.NewsArticleRequest;
import be.milete.app.news.article.NewsArticleResponse;
import be.milete.app.news.article.NewsArticleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.util.Assert.isTrue;

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

    @GetMapping(path = "/{id}/article")
    public List<NewsArticleResponse> getArticlesOfChannel(@PathVariable("id") int newsChannelId, @RequestParam("page") int page, @RequestParam("size") int size){
        isTrue(page > -1, "page must be greater than -1");
        isTrue(size > 0, "size must be positive number");
        isTrue(size < 51, "size must be lower than 51");

        return articleService.findPaginated(newsChannelId, page, size);
    }
}
