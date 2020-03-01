package at.spengergasse.presentation;

import at.spengergasse.documents.Reviews;
import at.spengergasse.persistence.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewRepository reviewRepository;

    public ReviewController(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @GetMapping
    public Mono<ResponseEntity<List<Reviews>>> getAllReviews() {
        return reviewRepository.findAll()
                .collectList()
                .flatMap(reviews -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(reviews)))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/{source}")
    public Mono<ResponseEntity<List<Reviews>>> getAllReviewsBySource(@PathVariable String source) {
        return reviewRepository.findAllBySource(source)
                .collectList()
                .flatMap(reviews -> Mono.just(ResponseEntity.status(HttpStatus.OK).body(reviews)))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    public Mono<ResponseEntity<Reviews>> addReview(@RequestBody Reviews review) {
        return reviewRepository.save(review).map(r -> new ResponseEntity<>(r, HttpStatus.OK))
                .onErrorReturn(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
    }
}