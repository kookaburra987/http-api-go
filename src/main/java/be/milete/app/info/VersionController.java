package be.milete.app.info;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.util.Assert.isTrue;

@RestController
@RequestMapping("info/version")
public class VersionController {

    private static final String VERSION = "0.0.1";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getVersion(){
        return VERSION;
    }

    @PostMapping(value = "equals")
    @ResponseStatus(HttpStatus.OK)
    public boolean isVersion(@RequestBody String otherVersion){
        isTrue(isNotBlank(otherVersion), "version must not be blank");
        isTrue(otherVersion.length() < 16, "version must be max 16 characters");
        String trimmedVersion = otherVersion.trim();

        return VERSION.equals(trimmedVersion);
    }
}
