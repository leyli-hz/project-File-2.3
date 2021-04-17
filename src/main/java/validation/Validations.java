package validation;

import exceptions.NotFoundObjException;
import org.apache.log4j.Logger;
import view.PardakhtVO;

import java.util.List;

public class Validations {
    private final Logger logger = Logger.getLogger(Validations.class);

    public boolean isDebtorExist(List<PardakhtVO> pardakhtVOS) throws NotFoundObjException {
        logger.debug("validations.isDebtorExist method is runnig. ");
        for (PardakhtVO pardakhtVO : pardakhtVOS) {
            if (pardakhtVO.getActionType().name().equalsIgnoreCase("debtor")) {
                return true;
            }
        }
        logger.debug("debtor account does not exist .");
        throw new NotFoundObjException("There is no Debtor Deposit number please check the pardakht File");
    }

    public boolean isCreditorExist(List<PardakhtVO> pardakhtVOS) throws NotFoundObjException {
        logger.debug("validations.isCreditorExist method is runnig .");
        for (PardakhtVO pardakhtVO : pardakhtVOS) {
            if (pardakhtVO.getActionType().name().equalsIgnoreCase("creditor")) {
                return true;
            }
        }
        logger.debug("creditor account does not exist .");
        return false;
    }
}
