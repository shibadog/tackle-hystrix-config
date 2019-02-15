package jp.shibadog.tackle.hystrixconfig.windowsize;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class WindowSizeConfig {

    private static Map<String, Long> settings = new ConcurrentHashMap<>();

    public long getErrorPercentage() {
        return settings.getOrDefault("errorPercentage", 0L);
    }

    public void setErrorPercentage(long errorPercentage) {
        settings.put("errorPercentage", errorPercentage);
    }
}