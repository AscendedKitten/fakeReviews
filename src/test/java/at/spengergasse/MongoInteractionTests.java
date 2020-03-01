package at.spengergasse;

import at.spengergasse.documents.Reviews;
import at.spengergasse.persistence.ReviewRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class MongoInteractionTests {

    private static ReviewRepository reviewRepository;

    @Autowired
    public MongoInteractionTests(ReviewRepository reviewRepository) {
        MongoInteractionTests.reviewRepository = reviewRepository;
    }

    @AfterEach
    public void cleanUp() {
        reviewRepository.deleteAll().block();
    }

    @Test
    public void ensureCanInsertAndRetrieve() {
        reviewRepository.save(new Reviews("reinthaler-wien", "Great place, would visit again")).block();
        Flux<Reviews> retrieved = reviewRepository.findAllBySource("reinthaler-wien");

        StepVerifier.create(retrieved)
                .expectNextMatches(review -> review.getContent().contains("Great place"))
                .verifyComplete();
    }
}
