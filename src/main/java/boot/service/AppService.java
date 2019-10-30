package boot.service;

import boot.mongo.dto.ReportDTO;
import boot.mongo.meta.MetaServiceClient;
import boot.mongo.meta.MetaServiceResponse;
import boot.mongo.model.MdicFormVersion;
import boot.mongo.model.MdicPeriodKindList;
import boot.mongo.model.StatBin;
import boot.mongo.repository.StatBinRepository;
import com.mongodb.client.model.Sorts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AppService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private StatBinRepository statBinRepository;

    @Autowired
    private MetaServiceClient metaServiceClient;

    //kolichestva catalog
    public List<StatBin> inCatalog(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode) {
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWith(periodKindListId, formId, active, inCatalog, teCode);

    }

    //otchitavwiesia
    public List<StatBin> otchitavwiesia(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode, List<String> statusCode) {
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, formId, active, inCatalog, teCode, statusCode);
    }

    //pereotchet
    public List<StatBin> pereotchet(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode, List<String> statusCode) {
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCode(periodKindListId, formId, active, inCatalog, teCode, statusCode);
    }

    //dozapis
    public List<StatBin> dozapis(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode, List<String> statusCode, Boolean doZapis) {
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCodeIsInAndDoZapis(periodKindListId, formId, active, inCatalog, teCode, statusCode, doZapis);
    }

    //forms
    public List<StatBin> allForm(Boolean active, List<Long> periodKindListId, String teCode) {
        teCode = "^" + teCode;
        Aggregation aggregation = newAggregation(
                match(
                        Criteria.where("active").exists(active).and("periodKindListId").in(periodKindListId).and("teCode").regex(teCode)
                ),
                group("formId", "periodKindListId"),
                sort(Sort.Direction.ASC,"formId")
        );
        AggregationResults<StatBin> results = mongoTemplate.aggregate(aggregation, "stat_bin", StatBin.class);
        List<StatBin> report = results.getMappedResults();
        return report;
    }

    public List<ReportDTO> getReports(List<Long> periodKindListId, String teCode, List<String> statusCode) {
        List<ReportDTO> reports = new ArrayList<>();

        List<StatBin> statBinList = allForm(true, periodKindListId, teCode);

        for (StatBin statBin : statBinList) {
            ReportDTO reportDTO = new ReportDTO();
            long cntCatalog = inCatalog(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode).size();
            long cntOtchitavwiesia = otchitavwiesia(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode).size();
            long cntPereotchet = pereotchet(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode).size();
            long cntDozapis = dozapis(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode, true).size();
            List<MdicPeriodKindList> period = metaServiceClient.getPeriod(statBin.getPeriodKindListId().get(0));
            MdicPeriodKindList mdicPeriodKindList = period.get(0);
            String title = getTitleForDetailStatBinWin(statBin.getFormId());

            reportDTO.setNum(statBinList.indexOf(statBin) + 1);
            reportDTO.setFormId(statBin.getFormId());
            reportDTO.setFormName(title);
            reportDTO.setPeriodKindlistName(mdicPeriodKindList.getPeriodKindlistName());
            reportDTO.setPeriodKindListId(statBin.getPeriodKindListId().get(0));
            reportDTO.setCntCatalog(cntCatalog);
            reportDTO.setOtchitavwisya(cntOtchitavwiesia);
            reportDTO.setPereotchet(cntPereotchet);
            reportDTO.setDozapis(cntDozapis);

            reports.add(reportDTO);
        }

        return reports;
    }

    public String getTitleForDetailStatBinWin(Long formId) {


        String title = "Форма: ";

        MetaServiceResponse<MdicFormVersion> metaServiceResponse =
                metaServiceClient.getFormById(formId);

        title += metaServiceResponse.getObj().getName() + ".";


        return title;

    }

    public List<String> getperiodKindlistName(Long periodKindListId){

        List<String> periodName = new ArrayList<>();
        List<MdicPeriodKindList> serviceResponse = metaServiceClient.getPeriod(periodKindListId);
        for (MdicPeriodKindList mdicPeriodKindList: serviceResponse){
            String name = mdicPeriodKindList.getPeriodKindlistName();
            periodName.add(name);
        }

        return periodName;
    }



}
