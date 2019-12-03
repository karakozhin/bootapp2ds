package boot.controller;

import boot.mongo.dto.EcpDTO;
import boot.mongo.dto.ReportDTO;
import boot.mongo.klazz.KlazzService;
import boot.mongo.klazz.Krp;
import boot.mongo.klazz.Region;
import boot.mongo.model.StatBin;
import boot.service.AppService;
import boot.service.Export;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    @Autowired
    private AppService appService;

    @Autowired
    private KlazzService klazzService;

    @Autowired
    private Export export;

    //kolichestva catalog
    @GetMapping("/cntCatalog")
    public List<StatBin> inCatalog(@RequestParam("periodKindListId") Long periodKindListId,
                                   @RequestParam("formId") Long formId,
                                   @RequestParam("active") Boolean active,
                                   @RequestParam("inCatalog") Boolean inCatalog,
                                   @RequestParam("teCode") String teCode) {
        return appService.inCatalogByForm(periodKindListId, formId, active, inCatalog, teCode);
    }

    //otchitavwiesia
    @GetMapping("/otchitavwisya")
    public List<StatBin> otchitavwiesia(@RequestParam("periodKindListId") Long periodKindListId,
                                        @RequestParam("formId") Long formId,
                                        @RequestParam("active") Boolean active,
                                        @RequestParam("inCatalog") Boolean inCatalog,
                                        @RequestParam("teCode") String teCode,
                                        @RequestParam("statusCode") List<String> statusCode) {
        return appService.otchitavwiesia(periodKindListId, formId, active, inCatalog, teCode, statusCode);
    }

    //pereotchet
    @GetMapping("/pereotchet")
    public List<StatBin> pereotchet(@RequestParam("periodKindListId") Long periodKindListId,
                                    @RequestParam("formId") Long formId,
                                    @RequestParam("active") Boolean active,
                                    @RequestParam("inCatalog") Boolean inCatalog,
                                    @RequestParam("teCode") String teCode,
                                    @RequestParam("statusCode") List<String> statusCode) {
        return appService.pereotchet(periodKindListId, formId, active, inCatalog, teCode, statusCode);
    }

    //dozapis
    @GetMapping("/dozapis")
    public List<StatBin> dozapis(@RequestParam("periodKindListId") Long periodKindListId,
                                 @RequestParam("formId") Long formId,
                                 @RequestParam("active") Boolean active,
                                 @RequestParam("inCatalog") Boolean inCatalog,
                                 @RequestParam("teCode") String teCode,
                                 @RequestParam("statusCode") List<String> statusCode,
                                 @RequestParam("doZapis") Boolean doZapis) {
        return appService.dozapis(periodKindListId, formId, active, inCatalog, teCode, statusCode, doZapis);
    }

    //forms
    @GetMapping("/forms")
    public List<StatBin> formAndPeriod(@RequestParam("active") Boolean active,
                                       @RequestParam("periodKindListId") List<Long> periodKindListId,
                                       @RequestParam("teCode") String teCode) {
        return appService.allForm(active, periodKindListId, teCode);
    }

    @GetMapping("/reports")
    public List<ReportDTO> getReports(@RequestParam("periodKindListId") List<Long> periodKindListId,
                                      @RequestParam("teCode") String teCode,
                                      @RequestParam("statusCode") List<String> statusCode) {
        return appService.getReports(periodKindListId, teCode, statusCode);
    }

    @GetMapping("/excel")
    public Workbook getExcel(@RequestParam("periodKindListId") List<Long> periodKindListId,
                             @RequestParam("teCode") String teCode,
                             @RequestParam("statusCode") List<String> statusCode){
        return export.getExcel(periodKindListId, teCode, statusCode);
    }

    @GetMapping("/name")
    public String getName(@RequestParam("formId") Long formId) {
        return appService.getTitleForDetailStatBinWin(formId);
    }

    @GetMapping("/periodKindlistName")
    public List<String> getperiodKindlistName(@RequestParam("periodKindListId") Long periodKindListId) {
        return appService.getperiodKindlistName(periodKindListId);
    }

    @GetMapping("/excelRegion")
    public Workbook getExcelRegion(@RequestParam("periodKindListId") Long periodKindListId){
        return export.getExcelRegion(periodKindListId);
    }

    //sdavwie po ECP
    @GetMapping("/findBySourceCode")
    public Long findStatBinBySourceCode(@RequestParam("periodKindListId") Long periodKindListId,
                                            @RequestParam("sourceCode") String sourceCode,
                                            @RequestParam("teCode") String teCode,
                                            @RequestParam("statusCode") List<String> statusCode){
        return appService.findBySourceCode(periodKindListId, sourceCode, teCode, statusCode);
    }

    @GetMapping("/region")
    public List<Region> getAllRegions() {
        return klazzService.getRegionList();
    }

    @GetMapping("/krp")
    public List<Krp> getKrpList(){
        return klazzService.getKrpList();
    }

//    @GetMapping("/countBySourceCode")
//    public Mono<Long> getCountStatBinBySourceCode(@RequestParam("periodKindListId") Long periodKindListId,
//                                                  @RequestParam("inCatalog") Boolean inCatalog,
//                                                  @RequestParam("sourceCode") String sourceCode,
//                                                  @RequestParam("teCode") String teCode,
//                                                  @RequestParam("statusCode") List<String> statusCode){
//        return appService.getCountStatBinBySourceCode(periodKindListId, inCatalog, sourceCode, teCode, statusCode);
//    }

    @GetMapping("/getOur")
    public List<EcpDTO> getEcpList(@RequestParam("periodKindListId") Long periodKindListId){
        return appService.getOur(periodKindListId);
    }
}
