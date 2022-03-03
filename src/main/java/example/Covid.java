package example;

import logging.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Covid {

    Integer cases;
    Integer oldCases;

    public void setCases(Integer newCases) {
        Logger logger = LoggerFactory.getLogger(Covid.class);

        if (newCases < 0) {
            logger.error("The number of cases cannot be negative!");
        } else {

            oldCases = cases;
            cases = newCases;

            logger.info("Number of Covid cases: {}", cases);
            logger.debug("Old number of cases was {}.", oldCases);

            if (cases > 3) {
                logger.warn("Please work from home!");
            }
        }
    }

    public Integer getCases() {
        return cases;
    }

    public static void main(String[] args) {
        LoggerConfig.setUp();
        Logger logger = LoggerFactory.getLogger(Covid.class);
        logger.info("Entering application.");
        example();
        logger.info("Resetting up the log level");
        LoggerConfig.setLogLevel(logger);
        example();
    }

    public static void example() {
        Covid covid = new Covid();
        covid.setCases(0);
        covid.setCases(2);
        covid.setCases(5);
        covid.setCases(-1);
        covid.setCases(0);
        covid.setCases(2);
        covid.setCases(5);
    }
}
