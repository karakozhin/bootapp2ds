package boot.service;

import boot.mongo.dto.ReportDTO;
import boot.mongo.model.StatBin;
import boot.mongo.repository.StatBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AppService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private StatBinRepository statBinRepository;

    //kolichestva catalog
    public List<StatBin> inCatalog(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode){
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWith(periodKindListId, formId, active, inCatalog, teCode);

    }

    //otchitavwiesia
    public List<StatBin> otchitavwiesia(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode, List<String> statusCode){
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, formId, active, inCatalog, teCode, statusCode);
    }

    //pereotchet
    public List<StatBin> pereotchet(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode, List<String> statusCode){
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCode(periodKindListId, formId, active, inCatalog, teCode, statusCode);
    }

    //dozapis
    public List<StatBin> dozapis(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode, List<String> statusCode, Boolean doZapis){
        return statBinRepository.findByPeriodKindListIdAndFormIdAndActiveAndInCatalogAndTeCodeStartsWithAndStatusCodeIsInAndDoZapis(periodKindListId, formId, active, inCatalog, teCode, statusCode, doZapis);
    }

    //forms
    public List<StatBin> allForm(Boolean active, List<Long> periodKindListId, String teCode){
        teCode = "^" + teCode;
        Aggregation aggregation = newAggregation(
                match(
                        Criteria.where("active").exists(active).and("periodKindListId").in(periodKindListId).and("teCode").regex(teCode)
                ),
                group("formId", "periodKindListId")
        );
        AggregationResults<StatBin> results = mongoTemplate.aggregate( aggregation, "stat_bin", StatBin.class);
        List<StatBin> report = results.getMappedResults();
        return report;
    }

    public List<ReportDTO> getReports(List<Long> periodKindListId, String teCode, List<String> statusCode) {
        List<ReportDTO> reports = new ArrayList<>();

        List<StatBin> statBinList = allForm(true, periodKindListId, teCode);

        for (StatBin statBin : statBinList) {
            ReportDTO reportDTO = new ReportDTO();
            long cntCatalog = this.inCatalog(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode).size();
            long cntOtchitavwiesia = this.otchitavwiesia(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode).size();
            long cntPereotchet = this.pereotchet(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode).size();
            long cntDozapis = this.dozapis(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode, true).size();


            reportDTO.setFormId(statBin.getFormId());
            reportDTO.setPeriodKindListId(statBin.getPeriodKindListId().get(0));
            reportDTO.setCntCatalog(cntCatalog);
            reportDTO.setOtchitavwisya(cntOtchitavwiesia);
            reportDTO.setPereotchet(cntPereotchet);
            reportDTO.setDozapis(cntDozapis);

            reports.add(reportDTO);
        }

        return reports;
    }

}
