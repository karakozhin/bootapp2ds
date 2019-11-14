package boot.service;

import boot.mongo.dto.EcpDTO;
import boot.mongo.dto.ReportDTO;
import boot.mongo.klazz.*;
import boot.mongo.meta.MetaServiceClient;
import boot.mongo.meta.MetaServiceResponse;
import boot.mongo.model.MdicFormVersion;
import boot.mongo.model.MdicPeriodKindList;
import boot.mongo.model.StatBin;
import boot.mongo.repository.StatBinReactiveRepository;
import boot.mongo.repository.StatBinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class AppService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private StatBinRepository statBinRepository;

    @Autowired
    private StatBinReactiveRepository statBinReactiveRepository;

    @Autowired
    private MetaServiceClient metaServiceClient;

    @Autowired
    private KlazzService klazzService;

    @Autowired
    private KlazzServiceClient klazzServiceClient;

    //kolichestva catalog po forme
    public List<StatBin> inCatalogByForm(Long periodKindListId, Long formId, Boolean active, Boolean inCatalog, String teCode) {
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

    //sdavwie po ECP
    public List<StatBin> findBySourceCode(Long periodKindListId, Boolean inCatalog, Boolean active, String sourceCode, String teCode, List<String> statusCode){
        return statBinRepository.findByPeriodKindListIdAndInCatalogAndActiveAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, inCatalog, active, sourceCode, teCode, statusCode);
    }

    //kolichestvo sdavwih po ECP
    public Mono<Long> getCountStatBinBySourceCode(Long periodKindListId, Boolean inCatalog, String sourceCode, String teCode, List<String> statusCode){
        return statBinReactiveRepository.countAllByPeriodKindListIdAndInCatalogAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, inCatalog, sourceCode, teCode, statusCode);
    }

    //kolichestva catalog
    public List<StatBin> getCountInCatalog(Long periodKindListId, Boolean active, Boolean inCatalog, String teCode){
        return statBinRepository.findByPeriodKindListIdAndActiveAndInCatalogAndTeCodeStartsWith(periodKindListId, active, inCatalog, teCode);
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

    //вытаскивает отчеты которые сдали по ЕЦП по областям согласно КРП(Классификатор размерности предприятий) и в процентах
    public List<EcpDTO> getOur(Long periodKindListId){
        List<String> status = new ArrayList<>();
        status.add("REPORTED");
        status.add("RECOUNTED");
        List<EcpDTO> ecpDTOList = new ArrayList<>();

        List<Region> regionList = klazzService.getRegionList();
        String proc = "%";
        for (Region region  : regionList){
            EcpDTO ecpDTO = new EcpDTO();

            //teCode вытащил
            String code = region.getTeCode();
            //передал teCode
            long passEcp = findBySourceCode(periodKindListId, true, true, "RESPONDENT", code, status).size();
            long cntCatalog = getCountInCatalog(periodKindListId, true, true, code).size();
            long procent = (passEcp * 100)/cntCatalog;

            ecpDTO.setOblName(region.getName());
            ecpDTO.setKolECP(passEcp);
            ecpDTO.setCntCatalog(cntCatalog);
            ecpDTO.setProcent(procent + proc);
            ecpDTOList.add(ecpDTO);
        }
        return ecpDTOList;
    }

    //по областям,периоду вытаскивает отчеты otchitavwiesia, kolichestva catalog, pereotchet, dozapis
    public List<ReportDTO> getReports(List<Long> periodKindListId, String teCode, List<String> statusCode) {
        List<ReportDTO> reports = new ArrayList<>();

        List<StatBin> statBinList = allForm(true, periodKindListId, teCode);

        for (StatBin statBin : statBinList) {
            ReportDTO reportDTO = new ReportDTO();
            long cntCatalog = inCatalogByForm(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode).size();
            long cntOtchitavwiesia = otchitavwiesia(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode).size();
            long cntPereotchet = pereotchet(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode).size();
            long cntDozapis = dozapis(statBin.getPeriodKindListId().get(0), statBin.getFormId(), true, true, teCode, statusCode, true).size();
            List<MdicPeriodKindList> period = metaServiceClient.getPeriod(statBin.getPeriodKindListId().get(0));
            MdicPeriodKindList mdicPeriodKindList = period.get(0);
            String title = getTitleForDetailStatBinWin(statBin.getFormId());

            //нумерация
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
