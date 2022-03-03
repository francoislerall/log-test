package example;

import java.util.Random;

import logging.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Covid {

    static Logger logger = LoggerFactory.getLogger(Covid.class);

    Integer cases;
    Integer oldCases;

    public void setCases(Integer newCases) {
        if (newCases < 0) {
            logger.error("The number of cases cannot be negative!");
        } else {

            oldCases = cases;
            cases = newCases;

            logger.debug("Number of Covid cases set to {}. Old number of cases was {}.", cases, oldCases);

            if (cases > 3) {
                logger.warn("Please work from home!");
            }
        }
    }

    public Integer getCases() {
        return cases;
    }

    public static void main(String[] args) {
        logger.info("Entering application.");
        example();
        logger.info("Resetting up the log level");
        LoggerConfig.setUp();
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
