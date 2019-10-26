package boot.mongo.model;

public class Test {

    private Id _id;

    public Id get_id() {
        return _id;
    }

    public void set_id(Id _id) {
        this._id = _id;
    }

    class Id {
        private Long formId;
        private Long periodKindListId;

        public Long getFormId() {
            return formId;
        }

        public void setFormId(Long formId) {
            this.formId = formId;
        }

        public Long getPeriodKindListId() {
            return periodKindListId;
        }

        public void setPeriodKindListId(Long periodKindListId) {
            this.periodKindListId = periodKindListId;
        }
    }
}
