package boot.mongo.model;

import java.io.Serializable;

public class MdicFormVersion implements Serializable {

    private Long id;
    private String name;
    private String periodic;


    public MdicFormVersion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriodic() {
        return periodic;
    }

    public void setPeriodic(String periodic) {
        this.periodic = periodic;
    }
}
