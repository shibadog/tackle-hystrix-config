package jp.shibadog.tackle.hystrixconfig.timeout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test/timeout")
@Slf4j
public class TimeOutController {

    @Autowired
    private TimeOutHystrix service;

    @RequestMapping({"/", ""})
    public String test() {
        log.info("これからうごかすお！");
        String res = service.execute();
        log.info("かえってきたお！: {}", res);
        return res;
    }
    
    @RequestMapping({"/{sec}"})
    public String test(@PathVariable long sec) {
        log.info("これからうごかすお！: {}s", sec);
        String res = service.execute(sec);
        log.info("かえってきたお！: {}", res);
        return res;
    }

    @RequestMapping({"/notimeout", "/notimeout/"})
    public String notimeout() {
        log.info("これからうごかすお！: 6s");
        String res = service.notimeout(6L);
        log.info("かえってきたお！: {}", res);
        return res;
    }

    @RequestMapping({"/notimeout/{sec}/{sec2}"})
    public String timeout(@PathVariable long sec, @PathVariable int sec2) {
        log.info("これからうごかすお！: {}s", sec);
        log.info("restTemplateのタイムアウト: {}s", sec2);
        String res = service.notimeout(sec, sec2);
        log.info("かえってきたお！: {}", res);
        return res;
    }
}