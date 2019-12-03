package boot.mongo.klazz;

public class Krp implements Comparable<Krp>{

    private Long id;
    private String code;
    private String name;

    public Krp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Krp o) {
        return name.compareTo(o.name);
    }
}
