package controller;

import org.apache.log4j.Logger;
import view.ViewPardakht;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Pardakht {
    private Path path = Paths.get("Files/pardakht.txt");
    private final Logger logger = Logger.getLogger(Pardakht.class);

    public Path getPath() {
        return path;
    }

    public Pardakht setPath(Path path) {
        this.path = path;
        return this;
    }

    public List<ViewPardakht> exportToViewP() {
        logger.debug("run Pardakht.export method");
        List<ViewPardakht> pardakhtList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(getPath());
            String line = bufferedReader.readLine();
            while (line != null) {
                pardakhtList.add(new ViewPardakht().setActionType(line.substring(0, line.indexOf("\t")))
                        .setDepositeNumb(line.substring(line.indexOf("\t") + 1, line.lastIndexOf("\t")))
                        .setMount(Long.parseLong(line.substring(line.lastIndexOf("\t") + 1, line.length()))));
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            logger.error("something went wrong ! please check if the Mojoodi.txt is available or not !'\r\n'" + e.getMessage());
        }
        return pardakhtList;
    }

    public Long sumOfCreditorMount(List<ViewPardakht> list) {
        logger.debug("run Pardakht.sum method");
        Long sum = 0l;
        for (ViewPardakht viewPardakht : list) {
            if (viewPardakht.getActionType().equals("c")) {
                sum += viewPardakht.getMount();
            }
        }
        return sum;
    }


}
