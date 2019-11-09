package boot.mongo.klazz;

import java.io.Serializable;

public class Kato implements Serializable {

    private Long itemId;
    private Long parent;
    private String code;
    private String name;
    private boolean leaf;
    private Long numberCatalog;

    public Kato() {
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
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

    public boolean isLeaf() {
        return leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public Long getNumberCatalog() {
        return numberCatalog;
    }

    public void setNumberCatalog(Long numberCatalog) {
        this.numberCatalog = numberCatalog;
    }
}
