package view;

import java.math.BigDecimal;

public class TransactionVo {
    private String sourceDeposit;
    private String destinationDeposit;
    private BigDecimal amount;

    public TransactionVo() {
    }

    public String getSourceDeposit() {
        return sourceDeposit;
    }

    public TransactionVo setSourceDeposit(String sourceDeposit) {
        this.sourceDeposit = sourceDeposit;
        return this;
    }

    public String getDestinationDeposit() {
        return destinationDeposit;
    }

    public TransactionVo setDestinationDeposit(String destinationDeposit) {
        this.destinationDeposit = destinationDeposit;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionVo setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "TransactionVo{" +
                "sourceDeposit='" + sourceDeposit + '\'' +
                ", destinationDeposit='" + destinationDeposit + '\'' +
                ", amount=" + amount +
                '}';
    }
}
