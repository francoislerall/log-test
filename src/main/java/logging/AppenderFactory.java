package logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.html.HTMLLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class AppenderFactory {

    public static RollingFileAppender<ILoggingEvent> createHtmlRollingAppender() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(context);
        appender.setName("html");

        PatternLayoutEncoder patternLayoutEncoder = new PatternLayoutEncoder();
        patternLayoutEncoder.setPattern("%date{HH:mm:ss.SSS} [%thread] %level %logger [%file:%line] %msg%n");
        patternLayoutEncoder.setContext(context);
        patternLayoutEncoder.start();


        TimeBasedRollingPolicy<ILoggingEvent> policy = new TimeBasedRollingPolicy<>();
        policy.setContext(context);
        String logLocation = "log";
        policy.setFileNamePattern(logLocation + "/log-%d{yyyy-MM-dd}.html");
        policy.setMaxHistory(3);
        policy.setTotalSizeCap(FileSize.valueOf("3MB"));
        policy.setParent(appender);
        policy.start();

        HTMLLayout layout = new HTMLLayout();
        layout.setContext(context);
        layout.setPattern("%d{HH:mm:ss.SSS}%thread%level%logger%line%msg");
        layout.start();

        LayoutWrappingEncoder<ILoggingEvent> encoder = new LayoutWrappingEncoder<>();
        encoder.setContext(context);
        encoder.setCharset(StandardCharsets.UTF_8);
        encoder.setLayout(layout);
        encoder.start();

        appender.setRollingPolicy(policy);
        appender.setEncoder(encoder);
        appender.setAppend(true);
        appender.start();

        return appender;
    }

    public static ConsoleAppender<ILoggingEvent> createConsoleAppender() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{HH:mm:ss.SSS} %green([%thread]) %highlight(%level) %logger{50} - %msg%n\"");
        encoder.start();

        ConsoleAppender<ILoggingEvent> appender = new ConsoleAppender<>();
        appender.setContext(context);
        appender.setName("console");
        appender.setEncoder(encoder);
        appender.start();

        return appender;
    }

    public static PopUpAppender createPopUpAppender() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(context);
        encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        PopUpAppender appender = new PopUpAppender();
        appender.setContext(context);
        appender.setName("popup");
        appender.setEncoder(encoder);
        appender.start();

        return appender;
    }

    static class PopUpAppender extends AppenderBase<ILoggingEvent> {
        final private Logger log = LoggerFactory.getLogger(this.getClass());

        private final int MAX_POPUPS = 5;
        private final int POPUP_REMOVAL_TIME = 5000;
        final private AtomicInteger openPopupCount = new AtomicInteger(0);
        final private AtomicInteger failureCounter = new AtomicInteger(0);

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
}