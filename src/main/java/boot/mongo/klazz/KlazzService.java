package boot.mongo.klazz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
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
                char[] array = kato.getCode().toCharArray();
                String str = new String(array, 0,2);
                kato.setCode(str);
                region.setTeCode(kato.getCode());
                region.setName(kato.getName());
                regionList.add(region);

            });
        }


        return regionList;
    }

    public List<Krp> getKrpList(){
        KlazzServiceResponse<Kato> katoKlazzServiceResponse = klazzServiceClient.getKrpList();

        List<Krp> krpList = new LinkedList<>();

        if (katoKlazzServiceResponse.isSuccess()){
            katoKlazzServiceResponse.getList().forEach(kato -> {
                Krp krp = new Krp();
//                krp.setId(kato.getItemId());
                krp.setName(kato.getName());
//                krp.setCode(kato.getCode());

                krpList.add(krp);
            });
        }
        return krpList;
    }
}
