package boot.mongo.klazz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class KlazzService {

    @Autowired
    private KlazzServiceClient klazzServiceClient;

    public List<Region> getRegionList(){

        KlazzServiceResponse<Kato> katoKlazzServiceResponse = klazzServiceClient.getKatoRegionList();

        List<Region> regionList = new LinkedList<>();

        if(katoKlazzServiceResponse.isSuccess()){
            katoKlazzServiceResponse.getList().parallelStream().forEach(kato -> {

                Region region = new Region();
                region.setId(kato.getItemId());
                region.setName(kato.getName());
                regionList.add(region);

            });
        }
        return regionList;
    }
}
