package controller;

import exceptions.NotFoundObjException;
import model.service.Validations;
import org.apache.log4j.Logger;
import view.MojoodiVO;
import view.PardakhtVO;
import view.TransactionVo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Mojoodi {
    private Path path = Paths.get("Files/mojoodi.txt");
    private final Logger logger = Logger.getLogger(Mojoodi.class);
    public static String payableAccount = "";

    public Path getPath() {
        return path;
    }

    public Mojoodi setPath(Path path) {
        this.path = path;
        return this;
    }

    public List<MojoodiVO> exportToMojoodiVO() {
        logger.debug("run Mojoodi.export method ");
        List<MojoodiVO> mojoodiList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(getPath());
            String line = bufferedReader.readLine();
            while (line != null) {
                mojoodiList.add(new MojoodiVO().setDepositNumb(line.substring(0, line.indexOf("\t")))
                        .setMount(new BigDecimal(line.substring(line.indexOf("\t") + 1, line.length()))));
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e1) {
            logger.error("something went wrong ! please check if the Mojoodi.txt is available or not !'\r\n'" + e1.getMessage());
        } catch (IOException e) {
            logger.error("something went wrong ! please check the Mojoodi.txt File!'\r\n'" + e.getMessage());
        }
        return mojoodiList;
    }

    public boolean checkTheMount(List<MojoodiVO> listMojoodi, List<PardakhtVO> listPardakht) throws NotFoundObjException {
        logger.debug("run Mojoodi.cheking method");
        Pardakht pardakht = new Pardakht();
        Validations validation = new Validations();
        BigDecimal required = BigDecimal.valueOf(Integer.MAX_VALUE);
        try {
            required = pardakht.sumOfCreditorMount(listPardakht);
        } catch (NotFoundObjException e) {
            logger.error(e.getMessage());
        }
        for (PardakhtVO pardakhtVO : listPardakht) {
            if (validation.isDebtorExist(listPardakht)) {
                if (pardakhtVO.getActionType().name().equals("d")) {
                    for (MojoodiVO mojoodiVO : listMojoodi) {
                        if (mojoodiVO.getDepositNumb().equals(pardakhtVO.getDepositeNumber())) {
                            //bozorgtar mosavi
                            if (mojoodiVO.getMount().compareTo(required) == 1 || mojoodiVO.getMount().compareTo(required) == 0) {
                                payableAccount = pardakhtVO.getDepositeNumber();
                                return true;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String updateDebtorMount(List<MojoodiVO> listMojoodi, List<PardakhtVO> listPardakht) {
        Validations validations = new Validations();
        logger.debug("run Mojoodi.update method for debtor");
        String finalString = "";

        for (PardakhtVO pardakhtVO : listPardakht) {
            if (pardakhtVO.getActionType().name().equals("d")) {
                for (MojoodiVO mojoodiVO : listMojoodi) {
                    if (mojoodiVO.getDepositNumb().equals(pardakhtVO.getDepositeNumber()) &&
                            mojoodiVO.getDepositNumb().equals(payableAccount)) {
                        BigDecimal oldMount = mojoodiVO.getMount();
                        BigDecimal updatedMount = oldMount.subtract(pardakhtVO.getAmount());
                        String updatedmount = String.valueOf(updatedMount);
                        mojoodiVO.setMount(updatedMount);
                        finalString = finalString.concat(mojoodiVO.getDepositNumb() + "\t" + updatedmount + "\r\n");
                        break;

                    } else if (mojoodiVO.getDepositNumb().equals(pardakhtVO.getDepositeNumber()) && !mojoodiVO.getDepositNumb().equals(payableAccount)) {
                        finalString = finalString.concat(mojoodiVO.getDepositNumb() + "\t" + mojoodiVO.getMount() + "\r\n");
                        break;
                    }
                }
            }
        }
        return finalString;
    }

    public String updateCreditorMount(List<MojoodiVO> listMojoodi, List<PardakhtVO> listPardakht) {
        logger.debug("run Mojoodi.update method for debtor");
        String finalString = "";
        List<TransactionVo> transactionVos = new ArrayList<>();
        Transaction transaction = new Transaction();
        for (PardakhtVO pardakhtVO : listPardakht) {
            if (pardakhtVO.getActionType().name().equals("c")) {
                for (MojoodiVO mojoodiVO : listMojoodi) {
                    if (mojoodiVO.getDepositNumb().equals(pardakhtVO.getDepositeNumber())) {
                        BigDecimal newMount = pardakhtVO.getAmount();
                        BigDecimal updatedMount = mojoodiVO.getMount().add(newMount);
                        String updatedmount = String.valueOf(updatedMount);
                        mojoodiVO.setMount(updatedMount);
                        transactionVos.add(new TransactionVo().setSourceDeposit(payableAccount)
                                .setDestinationDeposit(mojoodiVO.getDepositNumb())
                                .setAmount(newMount));
                        finalString = finalString.concat(mojoodiVO.getDepositNumb() + "\t" + updatedmount + "\r\n");
                        break;
                    }
                }
            }
        }
        transaction.makeLogContext(transactionVos);
        return finalString;
    }

    public void writeUpdatedMount(String updatedString) {
        logger.debug("run Mojoodi.write method to wtite on mojoodi.txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(updatedString);
        } catch (IOException e) {
            logger.error("something went wrong ! please check if the mojoodi.txt is available or not !'\r\n'" + e.getMessage());
            e.printStackTrace();
        }
    }
}
