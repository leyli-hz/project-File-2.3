package controller;

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

    public Path getPath() {
        return path;
    }

    public Mojoodi setPath(Path path) {
        this.path = path;
        return this;
    }

    public List<ViewMojoodi> exportToViewM() {
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
            e.printStackTrace();
        }
        return mojoodiList;
    }

    public String checkTheMount(List<ViewMojoodi> listMojoodi, List<ViewPardakht> listPardakht) {
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
                        finalString = finalString.concat(viewMojoodi.getDepositNumb() + "\t" + updatedmount + "\r\n");
                        break;
                    }
                }
            }
        }
        return finalString;
    }

    public void writeUpdatedMount(String updatedString) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(updatedString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
