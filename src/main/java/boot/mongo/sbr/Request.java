package boot.mongo.sbr;

import java.util.List;

public class Request {

    private String uuid;
    private List<String> binList;
    private Long formId;
    private String lang;
    private int rmonth;
    private int ryear;
    private String krp;
    private boolean needEmail = true;


    public Request() {
    }

    public List<String> getBinList() {
        return binList;
    }

    public void setBinList(List<String> binList) {
        this.binList = binList;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getRmonth() {
        return rmonth;
    }

    public void setRmonth(int rmonth) {
        this.rmonth = rmonth;
    }

    public int getRyear() {
        return ryear;
    }

    public void setRyear(int ryear) {
        this.ryear = ryear;
    }

    public boolean isNeedEmail() {
        return needEmail;
    }

    public void setNeedEmail(boolean needEmail) {
        this.needEmail = needEmail;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getKrp() {
        return krp;
    }

    public void setKrp(String krp) {
        this.krp = krp;
    }
}
