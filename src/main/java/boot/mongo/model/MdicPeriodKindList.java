package boot.mongo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MdicPeriodKindList implements Serializable {

    private Long id;
    private Long formVersionId;
    private Long dbeg;
    private Long dtResp;
    private Long dtObl;
    private Long dtFinal;
    private Long periodKindListId;
    private String periodKindlistName;
    private Long katregPeriod;
    private Long dtClass;
    private Long fversionId;

    public MdicPeriodKindList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormVersionId() {
        return formVersionId;
    }

    public void setFormVersionId(Long formVersionId) {
        this.formVersionId = formVersionId;
    }

    public Long getDbeg() {
        return dbeg;
    }

    public void setDbeg(Long dbeg) {
        this.dbeg = dbeg;
    }

    public Long getDtResp() {
        return dtResp;
    }

    public void setDtResp(Long dtResp) {
        this.dtResp = dtResp;
    }

    public Long getDtObl() {
        return dtObl;
    }

    public void setDtObl(Long dtObl) {
        this.dtObl = dtObl;
    }

    public Long getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Long dtFinal) {
        this.dtFinal = dtFinal;
    }
//
//    public List<Long> getPeriodKindListId() {
//        return periodKindListId;
//    }
//
//    public void setPeriodKindListId(List<Long> periodKindListId) {
//        this.periodKindListId = periodKindListId;
//    }


    public Long getPeriodKindListId() {
        return periodKindListId;
    }

    public void setPeriodKindListId(Long periodKindListId) {
        this.periodKindListId = periodKindListId;
    }

    public String getPeriodKindlistName() {
        return periodKindlistName;
    }

    public void setPeriodKindlistName(String periodKindlistName) {
        this.periodKindlistName = periodKindlistName;
    }

    public Long getKatregPeriod() {
        return katregPeriod;
    }

    public void setKatregPeriod(Long katregPeriod) {
        this.katregPeriod = katregPeriod;
    }

    public Long getDtClass() {
        return dtClass;
    }

    public void setDtClass(Long dtClass) {
        this.dtClass = dtClass;
    }

    public Long getFversionId() {
        return fversionId;
    }

    public void setFversionId(Long fversionId) {
        this.fversionId = fversionId;
    }
}
