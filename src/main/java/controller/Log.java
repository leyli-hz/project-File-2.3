package controller;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Log {
    private Path path = Paths.get("Files/log.txt");
    private final Logger logger = Logger.getLogger(Log.class);

    public void writeLog(String s) {
        logger.debug("run Log.writeLog method");
        try {
            Files.write(path, s.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("something went wrong ! please check if the log.txt is available or not !'\r\n'" + e.getMessage());
            e.printStackTrace();
        }
    }
}
