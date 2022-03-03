package logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;


public class LoggerConfig {

    public static void setUp() {
        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("example");
        setLogLevel(logger);
    }

    private static void setLogLevel(Logger logger) {
        // For testing purposes, the level of the log is set here.
        String level = "warn";
        if (level != null) {
            setLevelFromCommandLine(logger, level);
        }
        // If (level = null), the level of the logger stays default as defined in the `logback.xml` file.
        logger.info("Log level set to {}", logger.getLevel());
    }

    private static void setLevelFromCommandLine(Logger logger, String level) {
        if (level.equalsIgnoreCase("all")) {
            logger.setLevel(Level.ALL);
        } else if (level.equalsIgnoreCase("error")) {
            logger.setLevel(Level.ERROR);
        } else if (level.equalsIgnoreCase("info")) {
            logger.setLevel(Level.INFO);
        } else if (level.equalsIgnoreCase("warn")) {
            logger.setLevel(Level.WARN);
        } else {
            logger.setLevel(Level.INFO);
        }
    }
}
