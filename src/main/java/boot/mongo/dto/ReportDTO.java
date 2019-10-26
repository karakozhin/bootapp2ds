package boot.mongo.dto;

public class ReportDTO {

    private Long formId;
    private Long periodKindListId;
    private Long cntCatalog;
    private Long otchitavwisya;
    private Long pereotchet;
    private Long dozapis;

    public ReportDTO() {
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getPeriodKindListId() {
        return periodKindListId;
    }

    public void setPeriodKindListId(Long periodKindListId) {
        this.periodKindListId = periodKindListId;
    }

    public Long getCntCatalog() {
        return cntCatalog;
    }

    public void setCntCatalog(Long cntCatalog) {
        this.cntCatalog = cntCatalog;
    }

    public Long getOtchitavwisya() {
        return otchitavwisya;
    }

    public void setOtchitavwisya(Long otchitavwisya) {
        this.otchitavwisya = otchitavwisya;
    }

    public Long getPereotchet() {
        return pereotchet;
    }

    public void setPereotchet(Long pereotchet) {
        this.pereotchet = pereotchet;
    }

    public Long getDozapis() {
        return dozapis;
    }

    public void setDozapis(Long dozapis) {
        this.dozapis = dozapis;
    }
}
