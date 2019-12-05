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
import boot.mongo.sbr.SbrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    @Autowired
    private SbrService sbrService;

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
    public Long findBySourceCodeCnt(Long periodKindListId, String sourceCode, String teCode, List<String> statusCode){
         long g = statBinRepository.findByPeriodKindListIdAndInCatalogAndActiveAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, true, true, sourceCode, teCode, statusCode).size();
        return g;
    }

    public List<StatBin> findBySourceCode(Long periodKindListId, String sourceCode, String teCode, List<String> statusCode){
        return statBinRepository.findByPeriodKindListIdAndInCatalogAndActiveAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, true, true, sourceCode, teCode, statusCode);
    }

    //kolichestvo sdavwih po ECP
//    public Mono<Long> getCountStatBinBySourceCode(Long periodKindListId, Boolean inCatalog, String sourceCode, String teCode, List<String> statusCode){
//        return statBinReactiveRepository.countAllByPeriodKindListIdAndInCatalogAndSourceCodeAndTeCodeStartsWithAndStatusCodeIsIn(periodKindListId, inCatalog, sourceCode, teCode, statusCode);
//    }

    //kolichestva catalog
    public List<StatBin> getCountInCatalog(Long periodKindListId, String teCode){
        return statBinRepository.findByPeriodKindListIdAndActiveAndInCatalogAndTeCodeStartsWith(periodKindListId, true, true, teCode);
    }

//    public List<RespondentInfo> getInfo(String bin){
//        return sbrService.getRespondentInfoList(bin);
//    }

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

        //odin region sdavwie po ECP
    public EcpDTO getOneRegionInfo(Long periodKindListId){

        List<String> status = new ArrayList<>();
        status.add("REPORTED");
        status.add("RECOUNTED");

        EcpDTO ecpDTO = new EcpDTO();

        long passEcp = findBySourceCodeCnt(periodKindListId, "RESPONDENT", "55", status);
        List<StatBin> statBins = findBySourceCode(periodKindListId, "RESPONDENT", "55", status);

        long cntCatalog = getCountInCatalog(periodKindListId, "55").size();
        long procent = (statBins.size() * 100)/cntCatalog;

        String procent1 = "%";

        AtomicLong malyeCount = new AtomicLong();
        AtomicLong srednieCount = new AtomicLong();
        AtomicLong krupnyeCount = new AtomicLong();


        statBins.parallelStream().forEach(statBin -> {
            String bin = statBin.getBin();
            Long krp = Long.parseLong(sbrService.getInfo(bin).getKrp());


            if(krp>=0 & krp<=100){
                malyeCount.incrementAndGet();
            }
            else if (krp>=101 & krp<=250){
                srednieCount.incrementAndGet();
            }
            else {
                krupnyeCount.incrementAndGet();
            }
        });



        ecpDTO.setOblName("Pavlodar");
        ecpDTO.setCntCatalog(cntCatalog);
        ecpDTO.setKolECP(passEcp);
        ecpDTO.setProcent(procent+ procent1);
        ecpDTO.setMalye(malyeCount.get());
        ecpDTO.setSrednie(srednieCount.get());
        ecpDTO.setKrupnye(krupnyeCount.get());

        return ecpDTO;

    }

    //вытаскивает отчеты которые сдали по ЕЦП по областям согласно КРП(Классификатор размерности предприятий) и в процентах
    public List<EcpDTO> getOur(Long periodKindListId){

        List<String> status = new ArrayList<>();
        status.add("REPORTED");
        status.add("RECOUNTED");
        List<EcpDTO> ecpDTOList = new ArrayList<>();

        List<Region> regionList = klazzService.getRegionList();
        List<Krp> krpList = klazzService.getKrpList();

        String proc = "%";


      regionList.parallelStream().forEach(region ->
          {

            EcpDTO ecpDTO = new EcpDTO();

            //teCode вытащил
            String code = region.getTeCode();

            //передал teCode
            //sdavwie po ECP
            long passEcp = findBySourceCodeCnt(periodKindListId, "RESPONDENT", code, status);
            List<StatBin> statBins = findBySourceCode(periodKindListId, "RESPONDENT", code, status);


            //kolichestvo po katalogu
            long cntCatalog = getCountInCatalog(periodKindListId, code).size();
            long procent = (statBins.size() * 100)/cntCatalog;


              AtomicLong malyeCount = new AtomicLong();
              AtomicLong srednieCount = new AtomicLong();
              AtomicLong krupnyeCount = new AtomicLong();

              statBins.parallelStream().forEach(statBin -> {

                  String bin = statBin.getBin();
                  Long krp = Long.parseLong(sbrService.getInfo(bin).getKrp());

                  if(krp>=0 & krp<=100){
                      malyeCount.incrementAndGet();
                  }
                  else if (krp>=101 & krp<=250){
                      srednieCount.incrementAndGet();
                  }
                  else {
                      krupnyeCount.incrementAndGet();
                  }
              });


            ecpDTO.setOblName(region.getName());
            ecpDTO.setKolECP(passEcp);
            ecpDTO.setCntCatalog(cntCatalog);
            ecpDTO.setProcent(procent + proc);
              ecpDTO.setMalye(malyeCount.get());
              ecpDTO.setSrednie(srednieCount.get());
              ecpDTO.setKrupnye(krupnyeCount.get());
            ecpDTOList.add(ecpDTO);

        });


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
