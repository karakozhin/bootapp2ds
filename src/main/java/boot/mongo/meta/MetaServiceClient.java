package boot.mongo.meta;

import boot.mongo.model.MdicFormVersion;
import boot.mongo.model.MdicPeriodKindList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "metaData")
public interface MetaServiceClient {

    //принимает объект
    @RequestMapping(method = RequestMethod.GET, value = "/meta/form/id/{id}/ru", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    MetaServiceResponse<MdicFormVersion> getFormById(@PathVariable("id") Long formId);

    @RequestMapping(method = RequestMethod.GET, value = "/meta/calendarBase/periodKindListId/{periodKindListId}/ru", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    List<MdicPeriodKindList> getPeriod(@PathVariable("periodKindListId") Long periodKindListId);
}
