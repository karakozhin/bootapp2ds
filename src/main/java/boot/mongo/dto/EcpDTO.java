package boot.mongo.dto;

public class EcpDTO {

    private String oblName;
    private Long cntCatalog;
    private Long kolECP;
    private String procent;


    public EcpDTO() {
    }

    public String getOblName() {
        return oblName;
    }

    public void setOblName(String oblName) {
        this.oblName = oblName;
    }

    public Long getKolECP() {
        return kolECP;
    }

    public void setKolECP(Long kolECP) {
        this.kolECP = kolECP;
    }

    public String getProcent() {
        return procent;
    }

    public void setProcent(String procent) {
        this.procent = procent;
    }

    public Long getCntCatalog() {
        return cntCatalog;
    }

    public void setCntCatalog(Long cntCatalog) {
        this.cntCatalog = cntCatalog;
    }

}
