package jp.shibadog.tackle.hystrixconfig.windowsize;

import java.util.concurrent.ThreadLocalRandom;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WindowSizeHystrix {
    @Autowired
    private WindowSizeConfig conf;

    @HystrixCommand(
        groupKey = "windowsize",
        commandKey = "windowsize-run",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "120000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "99")
        }
    )
    public void run(boolean isError) {
        log.info("動くぜ");
        if (isError) {
            throw new RuntimeException();
        }
    }
    
    @HystrixCommand(
        groupKey = "windowsize",
        commandKey = "windowsize-controll",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "120000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "100")
        }
    )
    public void controll() {
        log.info("動くぜ");
        int random = ThreadLocalRandom.current().nextInt(100) + 1;
        long percent = conf.getErrorPercentage();
        if (random > percent || percent <= 0) {
            return;
        }
        throw new RuntimeException();
    }
}