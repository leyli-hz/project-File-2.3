package view;

import exceptions.NotFoundObjException;

import java.math.BigDecimal;

public class PardakhtVO {
    public enum ActionType {
        creditor, debtor;

        public static ActionType findIgnoreCase(String actionType) throws NotFoundObjException {
            for (ActionType value : ActionType.values()) {
                if (value.name().equalsIgnoreCase(actionType)) {
                    return value;
                }
            }
            throw new NotFoundObjException("There is a problem with deposit number's Action type . please check them in Pardakht file!");

        }
    }

    private BigDecimal amount;
    private String depositeNumber;
    private ActionType actionType;

    public PardakhtVO() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PardakhtVO setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getDepositeNumber() {
        return depositeNumber;
    }

    public PardakhtVO setDepositeNumber(String depositeNumber) {
        this.depositeNumber = depositeNumber;
        return this;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public PardakhtVO setActionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    @Override
    public String toString() {
        return "PardakhtVO{" +
                "amount=" + amount +
                ", depositeNumber='" + depositeNumber + '\'' +
                ", actionType=" + actionType +
                '}';
    }
}
