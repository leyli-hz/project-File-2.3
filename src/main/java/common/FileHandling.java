package common;

import exceptions.FilesException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileHandling {
    private static BigDecimal total = BigDecimal.ZERO;
    private final Logger logger = Logger.getLogger(FileHandling.class);

    public Path createFile(String filename) {
        logger.debug("FileHandling.createFile method is running to create : " + filename + " file.");
        Path path = Paths.get("Files/" + filename + ".txt");

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                logger.error("something went wrong while creating " + filename + " .Please try again!");
            }
            return path;
        } else {
            return path;
        }
    }

    public void writeOnFile(String context, Path path) throws FilesException {
        logger.debug("FileHandling.writeOnFile method is running .");
        try {
            Files.write(path, context.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new FilesException("Something went wrong while writing on Files !!", e);

        }
    }


    public String makePardakhtContext(String debtorDepositNum, String creditorDepositNum) {
        logger.debug("FileHandling.makeMojoodiContext method is running.");
        int max = 50000000;
        int min = 1;
        BigDecimal amount = BigDecimal.ZERO;
        String string = "";
        for (int i = 0; i < 1000; i++) {
            amount = BigDecimal.valueOf(Math.random() * (max - min) - min);
            total = total.add(amount.setScale(0, BigDecimal.ROUND_DOWN));

            string = string.concat("creditor" + "\t" + creditorDepositNum.replace(creditorDepositNum.substring(9, creditorDepositNum.length()), String.valueOf(i + 1)));
            string = string.concat("\t" + amount.setScale(0, BigDecimal.ROUND_DOWN).toString() + "\r\n");
        }
        string = string.concat("debtor" + "\t" + debtorDepositNum + "\t" + total.toString() + "\r\n");
        return string;
    }

    public String makeMojoodiContext(String debtorDepositNum, String creditorDepositNum) {
        logger.debug("FileHandling.makeMojoodiContext method is running.");
        String string = debtorDepositNum + "\t" + total.add(total.add(total)).toString() + "\r\n";
        for (int i = 0; i < 1000; i++) {
            string = string.concat(creditorDepositNum.replace(creditorDepositNum.substring(9, creditorDepositNum.length()), String.valueOf(i + 1)));
            string = string.concat("\t" + "0" + "\r\n");
        }
        return string;
    }

    public boolean isEmpty(Path path) {
        try {
            if (Files.size(path) != 0) {
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return true;
    }
}
