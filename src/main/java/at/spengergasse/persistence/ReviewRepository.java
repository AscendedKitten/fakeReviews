package at.spengergasse.persistence;

import at.spengergasse.documents.Reviews;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ReviewRepository extends ReactiveMongoRepository<Reviews, String> {
    Flux<Reviews> findAllBySource(String source);
}
