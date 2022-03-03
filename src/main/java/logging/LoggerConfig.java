package logging;

import java.nio.charset.StandardCharsets;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import example.Covid;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;


public class LoggerConfig {

    public static void setUp() {
        Logger rootLogger = (Logger)LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.WARN);
        setUpExampleLogger();
    }

    private static void setUpExampleLogger() {
        RollingFileAppender<ILoggingEvent> htmlAppender = AppenderFactory.createHtmlRollingAppender();
        ConsoleAppender<ILoggingEvent> consoleAppender = AppenderFactory.createConsoleAppender();
        AppenderFactory.PopUpAppender popUpAppender = AppenderFactory.createPopUpAppender();

        Logger logger = (Logger) LoggerFactory.getLogger("example");
        logger.addAppender(htmlAppender);
        logger.addAppender(consoleAppender);
        logger.addAppender(popUpAppender);
        logger.setLevel(Level.INFO);
    }

    public static void setLogLevel(org.slf4j.Logger logger) {
        // Getting the logback logger.
        Logger logbackLogger = (Logger) LoggerFactory.getLogger(Covid.class);
        // For testing purposes, the level of the log is set here.
        String level = "warn";
        if (level != null) {
            setLevelFromCommandLine(logbackLogger, level);
        }
        // If (level = null), the level of the logger stays default as defined in the `logback.xml` file.
        logger.info("Log level set to {}", logbackLogger.getLevel());
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

    // Not use here, but necessary to set the log levels via the app properties.
    public static void setLevel(Level level) {
        Logger logger = Objects.equal(level, Level.ALL) ?
                (Logger) LoggerFactory.getLogger("org.openlca") :
                (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        ;
        logger.setLevel(level);
        logger.info("Log level set to {}", logger.getLevel());
    }
}
