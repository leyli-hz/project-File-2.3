package controller;

import org.apache.log4j.Logger;
import view.ViewMojoodi;
import view.ViewPardakht;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Mojoodi {
    private Path path = Paths.get("Files/mojoodi.txt");
    private final Logger logger = Logger.getLogger(Mojoodi.class);

    public Path getPath() {
        return path;
    }

    public Mojoodi setPath(Path path) {
        this.path = path;
        return this;
    }

    public List<ViewMojoodi> exportToViewM() {
        logger.debug("run Mojoodi.export method ");
        List<ViewMojoodi> mojoodiList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(getPath());
            String line = bufferedReader.readLine();
            while (line != null) {
                mojoodiList.add(new ViewMojoodi().setDepositNumb(line.substring(0, line.indexOf("\t")))
                        .setMount(Long.parseLong(line.substring(line.indexOf("\t") + 1, line.length()))));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.error("something went wrong ! please check if the Mojoodi.txt is available or not !'\r\n'" + e.getMessage());
        }
        return mojoodiList;
    }

    public String checkTheMount(List<ViewMojoodi> listMojoodi, List<ViewPardakht> listPardakht) {
        logger.debug("run Mojoodi.cheking method");
        Pardakht pardakht = new Pardakht();
        Long required = pardakht.sumOfCreditorMount(listPardakht);

        for (ViewPardakht viewPardakht : listPardakht) {
            if (viewPardakht.getActionType().equals("d")) {
                for (ViewMojoodi viewMojoodi : listMojoodi) {
                    if (viewMojoodi.getDepositNumb().equals(viewPardakht.getDepositeNumb())) {
                        if (viewMojoodi.getMount() >= required) {
                            return viewPardakht.getDepositeNumb();
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    public String updateDebtorMount(List<ViewMojoodi> listMojoodi, List<ViewPardakht> listPardakht) {
        logger.debug("run Mojoodi.update method for debtor");
        String finalString = "";
        String payableAcc = checkTheMount(listMojoodi, listPardakht);

        for (ViewPardakht viewPardakht : listPardakht) {
            if (viewPardakht.getActionType().equals("d")) {
                for (ViewMojoodi viewMojoodi : listMojoodi) {
                    if (viewMojoodi.getDepositNumb().equals(viewPardakht.getDepositeNumb()) &&
                            viewMojoodi.getDepositNumb().equals(payableAcc)) {
                        Long oldMount = viewMojoodi.getMount();
                        Long updatedMount = oldMount - viewPardakht.getMount();
                        String updatedmount = String.valueOf(updatedMount);
                        viewMojoodi.setMount(updatedMount);
                        finalString = finalString.concat(viewMojoodi.getDepositNumb() + "\t" + updatedmount + "\r\n");
                        break;

                    } else if (viewMojoodi.getDepositNumb().equals(viewPardakht.getDepositeNumb()) && !viewMojoodi.getDepositNumb().equals(payableAcc)) {
                        finalString = finalString.concat(viewMojoodi.getDepositNumb() + "\t" + viewMojoodi.getMount() + "\r\n");
                        break;
                    }
                }
            }
        }
        return finalString;
    }

    public String updateCreditorMount(List<ViewMojoodi> listMojoodi, List<ViewPardakht> listPardakht) {
        logger.debug("run Mojoodi.update method for debtor");
        Log log = new Log();
        String finalString = "";
        String payableAcc = checkTheMount(listMojoodi, listPardakht);
        for (ViewPardakht viewPardakht : listPardakht) {
            if (viewPardakht.getActionType().equals("c")) {
                for (ViewMojoodi viewMojoodi : listMojoodi) {
                    if (viewMojoodi.getDepositNumb().equals(viewPardakht.getDepositeNumb())) {
                        Long oldMount = viewMojoodi.getMount();
                        Long newMount = viewPardakht.getMount();
                        Long updatedMount = oldMount + newMount;
                        String updatedmount = String.valueOf(updatedMount);
                        viewMojoodi.setMount(updatedMount);
                        log.writeLog(payableAcc + "\t" + viewMojoodi.getDepositNumb() + "\t" + newMount + "\r\n");
                        // Mojoodi.logger.info(payableAcc + "\t" + viewMojoodi.getDepositNumb() + "\t" + newMount );
                        finalString = finalString.concat(viewMojoodi.getDepositNumb() + "\t" + updatedmount + "\r\n");
                        break;
                    }
                }
            }
        }
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
