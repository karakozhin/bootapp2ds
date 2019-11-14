package boot.mongo.repository;

import boot.mongo.model.StatBin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface StatBinRepository extends MongoRepository<StatBin, String> {

    //kolichestva catalog po forme
    List<StatBin> findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWith(Long periodKindListId,
                                                                                          Long formId,
                                                                                          Boolean active,
                                                                                          Boolean inCatalog,
                                                                                          String teCode);

    //kolichestva catalog
    List<StatBin> findByPeriodKindListIdAndActiveAndInCatalogAndTeCodeStartsWith(Long periodKindListId,
                                                                                 Boolean active,
                                                                                 Boolean inCatalog,
                                                                                 String teCode);

    //otchitavwiesia
    List<StatBin> findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCodeIsIn(Long periodKindListId,
                                                                                                           Long formId,
                                                                                                           Boolean active,
                                                                                                           Boolean inCatalog,
                                                                                                           String teCode,
                                                                                                           List<String> statusCode);

    //pereotchet
    List<StatBin> findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCode(Long periodKindListId,
                                                                                                       Long formId,
                                                                                                       Boolean active,
                                                                                                       Boolean inCatalog,
                                                                                                       String teCode,
                                                                                                       List<String> statusCode);

    //dozapis
    List<StatBin> findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCodeIsInAndDoZapis(Long periodKindListId,
                                                                                                                     Long formId,
                                                                                                                     Boolean active,
                                                                                                                     Boolean inCatalog,
                                                                                                                     String teCode,
                                                                                                                     List<String> statusCode,
                                                                                                                     Boolean doZapis);

    //sdavwie po ECP
    List<StatBin> findByPeriodKindListIdAndInCatalogAndActiveAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn
            (Long periodKindListId, Boolean inCatalog, Boolean active, String sourceCode, String teCode, List<String> statusCode);
}
