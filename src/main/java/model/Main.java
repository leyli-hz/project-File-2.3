package model;

import controller.Mojoodi;
import controller.Pardakht;
import exceptions.FilesException;
import exceptions.NotFoundObjException;
import org.apache.log4j.Logger;
import view.MojoodiVO;
import view.PardakhtVO;

import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Logger logger = org.apache.log4j.Logger.getLogger(Main.class);
        Pardakht pardakht = new Pardakht();
        Mojoodi mojoodi = new Mojoodi();
//create Files
        FileHandling fileHandling = new FileHandling();
        fileHandling.createFile("transaction");
        fileHandling.createFile("mojoodi");
        fileHandling.createFile("pardakht");

        if (fileHandling.isEmpty(pardakht.getPath())) {
            String contentPardakht = fileHandling.makePardakhtContext("1.10.100.1", "2.20.200.1");
            try {
                fileHandling.writeOnFile(contentPardakht, Paths.get("Files/pardakht.txt"));
            } catch (FilesException e) {
                logger.error(e.getMessage(), e.getCause());
            }
        } else {
            System.out.println("#Pardakht file is not empty. paying salary will continue with previouse data.\r\n" +
                    "for new data Please delete file context before run the program!");
        }

        if (fileHandling.isEmpty(mojoodi.getPath())) {
            String contentMojoodi = fileHandling.makeMojoodiContext("1.10.100.1", "2.20.200.1");
            try {
                fileHandling.writeOnFile(contentMojoodi, Paths.get("Files/mojoodi.txt"));
            } catch (FilesException e) {
                logger.error(e.getMessage(), e.getCause());
            }
        } else {
            System.out.println("#Mojoodi file is not empty! paying salary will continue with previouse data.\r\n " +
                    "for new data Please delete file context before run the program!");
        }

        List<MojoodiVO> mojoodiVOS = mojoodi.exportToMojoodiVO();
        List<PardakhtVO> pardakhtVOS = pardakht.exportToPardakhtVO();
        boolean flag = false;
        try {
            flag = mojoodi.checkTheMount(mojoodiVOS, pardakhtVOS);
        } catch (NotFoundObjException e) {
            logger.error(e.getMessage());
        }
        if (flag) {
            logger.info("you can pay by this deposite number: " + Mojoodi.payableAccount);
            String updatedString = mojoodi.updateDebtorMount(mojoodiVOS, pardakhtVOS);
            updatedString = updatedString.concat(mojoodi.updateCreditorMount(mojoodiVOS, pardakhtVOS));
            mojoodi.writeUpdatedMount(updatedString);
            logger.info("the paying salary is done");
        } else {
            System.out.println("you dont have enough money ! please check your bank account.");
        }
    }
}
