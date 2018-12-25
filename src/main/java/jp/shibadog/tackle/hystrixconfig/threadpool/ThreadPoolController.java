package jp.shibadog.tackle.hystrixconfig.threadpool;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test/threadpool")
@Slf4j
public class ThreadPoolController {
    @Autowired
    private ThreadPoolHystrix service;

    @Autowired
    private AsyncService asyncService;
    
    @RequestMapping({"/", ""})
    public String threadpool() {
        log.info("これからうごかすお！");
        long count = IntStream.range(1, 6)
            .map(i -> {
                asyncService.async(service::execute, i);
                return i;
            }).count();
        log.info("動かし終わったお: {}本", count);
        return "OK";
    }
    
    @RequestMapping("/{cnt}")
    public String threadpool(@PathVariable int cnt) {
        log.info("これからうごかすお！");
        long count = IntStream.range(1, cnt + 1)
            .map(i -> {
                asyncService.async(service::execute, i);
                return i;
            }).count();
        log.info("動かし終わったお: {}本", count);
        return "OK";
    }
    
    @RequestMapping("/queue/{cnt}")
    public String threadpoolQueue(@PathVariable int cnt) {
        log.info("これからうごかすお！");
        long count = IntStream.range(1, cnt + 1)
            .map(i -> {
                asyncService.async(service::executeQueueing, i);
                return i;
            }).count();
        log.info("動かし終わったお: {}本", count);
        return "OK";
    }

}