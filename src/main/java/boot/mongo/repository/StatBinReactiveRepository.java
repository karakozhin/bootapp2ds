package boot.mongo.repository;

import boot.mongo.model.StatBin;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.List;

public interface StatBinReactiveRepository extends ReactiveMongoRepository<StatBin, String> {

    Mono<Long> countAllByPeriodKindListIdAndInCatalogAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn
            (Long periodKIndListId, Boolean inCatalog, String sourceCode, String teCode, List<String> statusCode);
}
