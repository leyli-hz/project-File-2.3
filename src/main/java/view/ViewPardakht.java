package view;

public class ViewPardakht {
    private Long mount;
    private String depositeNumb;
    private String actionType;

    public ViewPardakht() {
    }

    public ViewPardakht(Long mount, String depositeNumb, String actionType) {
        this.mount = mount;
        this.depositeNumb = depositeNumb;
        this.actionType = actionType;
    }

    public Long getMount() {
        return mount;
    }

    public ViewPardakht setMount(Long mount) {
        this.mount = mount;
        return this;
    }

    public String getDepositeNumb() {
        return depositeNumb;
    }

    public ViewPardakht setDepositeNumb(String depositeNumb) {
        this.depositeNumb = depositeNumb;
        return this;
    }

    public String getActionType() {
        return actionType;
    }

    public ViewPardakht setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }
}
