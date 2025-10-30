package be.milete.app.info;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("info/version")
public class VersionController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getVersion(){
        return "0.0.1";
    }

}
