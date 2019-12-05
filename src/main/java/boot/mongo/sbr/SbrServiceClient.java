package boot.mongo.sbr;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SBRDATA")
public interface SbrServiceClient {

    @RequestMapping(method = RequestMethod.POST,value = "/sbr/statUnity/fd/list")
    List<RespondentInfo> getRespondentList(@RequestBody Request request);

    @RequestMapping(method = RequestMethod.GET, value = "/sbr/respondent")
    RespondentInfo getInfo(@RequestParam("bin") String bin);

}
