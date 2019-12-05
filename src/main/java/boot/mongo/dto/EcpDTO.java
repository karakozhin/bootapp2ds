package boot.mongo.dto;

public class EcpDTO {

    private String oblName;
    private Long cntCatalog;
    private Long kolECP;
    private String procent;
    private Long malye;
    private Long srednie;
    private Long krupnye;


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

    public Long getMalye() {
        return malye;
    }

    public void setMalye(Long malye) {
        this.malye = malye;
    }

    public Long getSrednie() {
        return srednie;
    }

    public void setSrednie(Long srednie) {
        this.srednie = srednie;
    }

    public Long getKrupnye() {
        return krupnye;
    }

    public void setKrupnye(Long krupnye) {
        this.krupnye = krupnye;
    }
}
