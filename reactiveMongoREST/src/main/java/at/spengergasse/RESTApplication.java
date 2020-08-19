package at.spengergasse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class RESTApplication {
    public static void main(String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }
}
