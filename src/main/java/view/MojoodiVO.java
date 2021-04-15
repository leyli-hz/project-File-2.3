package view;

import java.math.BigDecimal;

public class MojoodiVO {
    private BigDecimal mount;
    private String depositNumb;

    public MojoodiVO() {
    }

    public BigDecimal getMount() {
        return mount;
    }

    public MojoodiVO setMount(BigDecimal mount) {
        this.mount = mount;
        return this;
    }

    public String getDepositNumb() {
        return depositNumb;
    }

    public MojoodiVO setDepositNumb(String depositNumb) {
        this.depositNumb = depositNumb;
        return this;
    }

    @Override
    public String toString() {
        return "MojoodiVO{" +
                "mount=" + mount +
                ", depositNumb='" + depositNumb + '\'' +
                '}';
    }
}
