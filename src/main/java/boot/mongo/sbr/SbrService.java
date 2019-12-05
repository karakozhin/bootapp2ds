package boot.mongo.sbr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SbrService {

    @Autowired
    private SbrServiceClient sbrServiceClient;

    public List<RespondentInfo> getRespondentInfoList(Request request){
        List<RespondentInfo> respondentInfoList = sbrServiceClient.getRespondentList(request);
        return respondentInfoList;
    }

    public RespondentInfo getInfo(String bin){
        return sbrServiceClient.getInfo(bin);
    }
}
