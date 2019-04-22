package tmp;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Loggers {
    private static Logger logger = Logger.getLogger(Loggers.class.getName());

    public static void main(String[] args) throws Exception {
        logger.fine("Fine message. " + logger.getLevel());
        logger.info("Information message. " + logger.getLevel());
        logger.warning("Warning message.");
        logger.severe("Severe message.");

        // Set the log level to Level.INFO
        logger.setLevel(Level.INFO);
        logger.severe("This message will be logged.");

        // Set the log level to Level.SEVERE
        logger.setLevel(Level.SEVERE);
        logger.warning("This message won't be logged.");

        // Turn of the log
        logger.setLevel(Level.OFF);
        logger.info("All log is turned off.");

        // Turn the logger on
        logger.setLevel(Level.ALL);
        logger.info("Information message.");
        logger.warning("Warning message.");
        logger.severe("Severe message.");
    }
}