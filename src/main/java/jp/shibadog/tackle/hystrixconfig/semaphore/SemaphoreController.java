package jp.shibadog.tackle.hystrixconfig.semaphore;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.shibadog.tackle.hystrixconfig.AsyncSharedService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test/semaphore")
@Slf4j
public class SemaphoreController {
    @Autowired
    private SemaphoreHystrix service;

    @Autowired
    private AsyncSharedService asyncService;
    
    @RequestMapping({"/one"})
    public String threadpool_one() {
        log.info("これからうごかすお！");
        service.execute(1);
        log.info("動かし終わったお");
        return "OK";
    }

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

}