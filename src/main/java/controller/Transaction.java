package controller;

import org.apache.log4j.Logger;
import view.TransactionVo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Transaction {
    private Path path = Paths.get("Files/transaction.txt");
    private final Logger logger = Logger.getLogger(Transaction.class);

    public void makeLogContext(List<TransactionVo> transactionVos) {
        String logContext = "";
        for (TransactionVo transactionVo : transactionVos) {
            logContext = logContext.concat(transactionVo.getSourceDeposit() + "\t" + transactionVo.getDestinationDeposit() + "\t"
                    + transactionVo.getAmount() + "\r\n");
        }
        writeLog(logContext);
    }

    public void writeLog(String logContext) {
        logger.debug("run Log.writeLog method");
        try {
            Files.write(path, logContext.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("something went wrong ! please check if the transaction.txt is available or not !'\r\n'" + e.getMessage());
            e.printStackTrace();
        }
    }
}
