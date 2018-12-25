package jp.shibadog.tackle.hystrixconfig.timeout;

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
public class TimeOutHystrix {

    @Autowired
    private ServerProperties serverProperties;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @HystrixCommand(
        commandKey = "timeout1",
        fallbackMethod = "fallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
        }
    )
    public String execute() {
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout", String.class);

        log.info("{}", "うまくうごいたお");

        return res;
    }
    
    @HystrixCommand(
        commandKey = "timeout2",
        fallbackMethod = "fallback",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
        }
    )
    public String execute(long sec) {
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout/" + sec, String.class);

        log.info("{}", "うまくうごいたお");

        return res;
    }

    @HystrixCommand(
        commandKey = "timeout3",
        fallbackMethod = "fallback",
        commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")
        }
    )
    public String notimeout(long sec, int sec2) {
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder
                                        .setReadTimeout(sec2 * 1000)
                                        .build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout/" + sec, String.class);

        log.info("{}", "うまくうごいたお");

        return res;
    }
    
    @HystrixCommand(
        commandKey = "timeout4",
        fallbackMethod = "fallback",
        commandProperties = {
            @HystrixProperty(name = "execution.timeout.enabled", value = "false")
        }
    )
    public String notimeout(long sec) {
        int port = serverProperties.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();

        String res = restTemplate.getForObject("http://localhost:" + port + "/timeout/" + sec, String.class);

        log.info("{}", "うまくうごいたお");

        return res;
    }

    public String fallback(Throwable t) {
        log.info("ふぉーるばっくしたお: {}", t);
        return "fallback";
    }

    public String fallback(long sec, Throwable t) {
        return fallback(t);
    }

    public String fallback(long sec, int sec2, Throwable t) {
        return fallback(t);
    }
}