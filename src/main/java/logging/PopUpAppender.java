package logging;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.Level;
import ch.qos.logback.core.AppenderBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class PopUpAppender extends AppenderBase<ILoggingEvent> {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final int MAX_POPUPS = 5;
    private final int POPUP_REMOVAL_TIME = 5000;
    private AtomicInteger openPopupCount = new AtomicInteger(0);
    private AtomicInteger failureCounter = new AtomicInteger(0);

    PatternLayoutEncoder encoder;

    @Override
    public void start() {
        super.start();
    }

    public void append(ILoggingEvent event) {
        if (event == null || !event.getLevel().isGreaterOrEqual(Level.ERROR))
            return;
        try {
            openPopupCount.incrementAndGet();
            String message = event.getMessage() != null ? event.getMessage().toString() : "";

            JOptionPane.showMessageDialog(null, message, "An internal error occurred", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            // If the creation of the popup creates an error itself
            // handle it here.
            if (failureCounter.incrementAndGet() > 3) {
                log.warn("Showing of failed error popups " + "stopped because of repetetive failures");
            } else {
                log.error("Show message failed", e);
            }
        }
    }

    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }
}