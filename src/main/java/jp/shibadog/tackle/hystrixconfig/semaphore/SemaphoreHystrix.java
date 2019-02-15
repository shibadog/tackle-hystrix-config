package jp.shibadog.tackle.hystrixconfig.semaphore;

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
public class SemaphoreHystrix {
    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @HystrixCommand(
        commandKey = "threadPool",
        fallbackMethod = "fallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "5"),
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

    
    public String fallback(int index) {
        log.info("{}本目->ふぉーるばっくしたお", index);
        return "fallback";
    }
}