package be.milete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"be.milete.app"})
public class HttpApiGoApplication {
    public static void main(String[] args) {
        SpringApplication.run(HttpApiGoApplication.class, args);
    }
}
