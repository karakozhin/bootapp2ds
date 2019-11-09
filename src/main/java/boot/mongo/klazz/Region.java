package boot.mongo.klazz;

public class Region {

    private Long id;
    private String teCode;
    private String name;

    public Region() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeCode() {
        return teCode;
    }

    public void setTeCode(String teCode) {
        this.teCode = teCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
