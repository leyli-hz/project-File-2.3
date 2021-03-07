package view;

public class ViewMojoodi {
    private Long mount;
    private String depositNumb;

    public ViewMojoodi() {
    }

    public ViewMojoodi(Long mount, String depositNumb) {
        this.mount = mount;
        this.depositNumb = depositNumb;
    }

    public Long getMount() {
        return mount;
    }

    public ViewMojoodi setMount(Long mount) {
        this.mount = mount;
        return this;
    }

    public String getDepositNumb() {
        return depositNumb;
    }

    public ViewMojoodi setDepositNumb(String depositNumb) {
        this.depositNumb = depositNumb;
        return this;
    }
}
