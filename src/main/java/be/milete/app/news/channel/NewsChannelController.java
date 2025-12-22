package be.milete.app.news.channel;

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

    public NewsChannelController(NewsChannelService service) {
        this.service = service;
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
}
