package be.milete.app.news;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @ResponseStatus(OK)
    @GetMapping
    public List<NewsChannelResponse> getAll(){
        return service.getAllNewsChannels();
    }
}
