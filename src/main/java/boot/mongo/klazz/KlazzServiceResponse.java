package boot.mongo.klazz;

import java.io.Serializable;
import java.util.List;

public class KlazzServiceResponse<T> implements Serializable {

    private boolean success;
    private T item;
    private List<T> list;
    private List<T> common;
    private Long totalCount;

    public KlazzServiceResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getCommon() {
        return common;
    }

    public void setCommon(List<T> common) {
        this.common = common;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
