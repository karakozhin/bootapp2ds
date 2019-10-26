package boot.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Document(collection = "stat_bin")
public class StatBin {

    @Id
    private String id;
    private String messageId;
    private String bin;
    private String statKod;
    private List<Long> periodKindListId;
    private Long formId;
    private Long teId;
    private List<String> statusCode;
    private String sourceCode;
    private boolean inCatalog; //1-da 0- net
    private Date dateUpdate;
    private String teCode;
    private boolean hasError;
    private boolean doZapis;
    private boolean isLate;
    private boolean active;
    private Boolean isPustoi;
    private String messageSource;
    private Date statusDateObl;
    private Date statusDateOO;
    private String jurName;
    private String addCat;
    private Date dateSentToSbr;
    private Date dateDozapis;

    public StatBin() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getStatKod() {
        return statKod;
    }

    public void setStatKod(String statKod) {
        this.statKod = statKod;
    }

    public List<Long> getPeriodKindListId() {
        return periodKindListId;
    }

    public void setPeriodKindListId(List<Long> periodKindListId) {
        this.periodKindListId = periodKindListId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public Long getTeId() {
        return teId;
    }

    public void setTeId(Long teId) {
        this.teId = teId;
    }

    public List<String> getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(List<String> statusCode) {
        this.statusCode = statusCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public boolean isInCatalog() {
        return inCatalog;
    }

    public void setInCatalog(boolean inCatalog) {
        this.inCatalog = inCatalog;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public String getTeCode() {
        return teCode;
    }

    public void setTeCode(String teCode) {
        this.teCode = teCode;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public boolean isDoZapis() {
        return doZapis;
    }

    public void setDoZapis(boolean doZapis) {
        this.doZapis = doZapis;
    }

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Boolean getPustoi() {
        return isPustoi;
    }

    public void setPustoi(Boolean pustoi) {
        isPustoi = pustoi;
    }

    public String getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(String messageSource) {
        this.messageSource = messageSource;
    }

    public Date getStatusDateObl() {
        return statusDateObl;
    }

    public void setStatusDateObl(Date statusDateObl) {
        this.statusDateObl = statusDateObl;
    }

    public Date getStatusDateOO() {
        return statusDateOO;
    }

    public void setStatusDateOO(Date statusDateOO) {
        this.statusDateOO = statusDateOO;
    }

    public String getJurName() {
        return jurName;
    }

    public void setJurName(String jurName) {
        this.jurName = jurName;
    }

    public String getAddCat() {
        return addCat;
    }

    public void setAddCat(String addCat) {
        this.addCat = addCat;
    }

    public Date getDateSentToSbr() {
        return dateSentToSbr;
    }

    public void setDateSentToSbr(Date dateSentToSbr) {
        this.dateSentToSbr = dateSentToSbr;
    }

    public Date getDateDozapis() {
        return dateDozapis;
    }

    public void setDateDozapis(Date dateDozapis) {
        this.dateDozapis = dateDozapis;
    }
}
