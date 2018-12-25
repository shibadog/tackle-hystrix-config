package jp.shibadog.tackle.hystrixconfig.timeout;

import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/timeout")
@Slf4j
public class TimeOutServer {

    @RequestMapping({"/", ""})
    public String timeout() {
        return sleep(2L);
    }

    @RequestMapping({"/{sec}"})
    public String sleep(@PathVariable long sec) {
        try {
            log.info("すりーぷするぜぃ: {}s", sec);
            TimeUnit.SECONDS.sleep(sec);
            log.info("すりーぷしたぜぃ: {}s", sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "OK " + sec + "s";
    }
}