package logging;

import ch.qos.logback.core.PropertyDefinerBase;


public class LogLocationPropertyDefiner extends PropertyDefinerBase {
    @Override
    public String getPropertyValue() {
        return "log";
        //To be replaced with "Platform.getLocation().toFile()"
    }
}
