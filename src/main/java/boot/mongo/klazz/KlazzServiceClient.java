package boot.mongo.klazz;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.awt.*;

@FeignClient(name = "RestKlazz")
public interface KlazzServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/GetChildernItemsByDateResource/213/741880/ru/2019-08-01", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    KlazzServiceResponse<Kato> getKatoRegionList();

    @RequestMapping(method = RequestMethod.GET, value = "GetChildernItemsResource/15/741927/ru", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    KlazzServiceResponse<Kato> getKrpList();
}
