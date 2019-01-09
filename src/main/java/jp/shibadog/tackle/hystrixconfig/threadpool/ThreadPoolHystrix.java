package jp.shibadog.tackle.hystrixconfig.threadpool;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ThreadPoolHystrix {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @HystrixCommand(
        commandKey = "threadPool",
        fallbackMethod = "fallback",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "3"),
            @HystrixProperty(name = "maxQueueSize", value = "-1")
        },
        commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")
        }
    )
    public String execute(int index) {
        log.info("{}本目->まかせとけぇぇぇぇぇぇ！", index);
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout/10", String.class);

        log.info("{}本目->うまくうごいたお", index);

        return res;
    }
    
    @HystrixCommand(
        commandKey = "threadPool-queue",
        fallbackMethod = "fallback",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "3"),
            @HystrixProperty(name = "maxQueueSize", value = "3")
        },
        commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")
        }
    )
    public String executeQueueing(int index) {
        log.info("{}本目->まかせとけぇぇぇぇぇぇ！", index);
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout/10", String.class);

        log.info("{}本目->うまくうごいたお", index);

        return res;
    }

    @HystrixCommand(
        commandKey = "threadPool-maximum",
        fallbackMethod = "fallback",
        threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "3"),
            @HystrixProperty(name = "maximumSize", value = "5"),
            @HystrixProperty(name = "maxQueueSize", value = "10"),
            @HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true")
        },
        commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")
        }
    )
    public String executeMaximum(int index) {
        log.info("{}本目->まかせとけぇぇぇぇぇぇ！", index);
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout/10", String.class);

        log.info("{}本目->うまくうごいたお", index);

        return res;
    }

    public String fallback(int index, Throwable t) {
        log.info("{}本目->ふぉーるばっくしたお", index);
        return "fallback";
    }
}