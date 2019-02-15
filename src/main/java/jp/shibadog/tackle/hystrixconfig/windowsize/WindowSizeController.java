package jp.shibadog.tackle.hystrixconfig.windowsize;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/test/windowsize")
@Slf4j
public class WindowSizeController {
    @Autowired
    private WindowSizeHystrix service;

    @Autowired
    private WindowSizeConfig conf;

    private ScheduledExecutorService executer = Executors.newSingleThreadScheduledExecutor();

    @RequestMapping({ "/run/ng" })
    public String runOk() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> future = 
            executer.scheduleAtFixedRate(() -> {
                try {
                    service.run(true);
                    log.info("正常！");
                } catch(HystrixRuntimeException e) {
                    log.info("HystrixによるOpenまたはReject");
                } catch(RuntimeException e) {
                    log.info("通常エラー発生");
                }
            }, 1_000, 1_000, TimeUnit.MILLISECONDS);
        future.get();
        executer.shutdown();
        return "OK";
    }
    
    @RequestMapping({ "/run/ok" })
    public String runNg() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> future = 
            executer.scheduleAtFixedRate(() -> {
                try {
                    service.run(false);
                    log.info("正常！");
                } catch(HystrixRuntimeException e) {
                    log.info("HystrixによるOpenまたはReject");
                } catch(RuntimeException e) {
                    log.info("通常エラー発生");
                }
            }, 1_000, 1_000, TimeUnit.MILLISECONDS);
        future.get();
        executer.shutdown();
        return "OK";
    }

    @RequestMapping({ "/run/controll" })
    public String controll() throws InterruptedException, ExecutionException {
        ScheduledFuture<?> future = 
            executer.scheduleAtFixedRate(() -> {
                try {
                    service.controll();
                    log.info("正常！");
                } catch(HystrixRuntimeException e) {
                    log.info("HystrixによるOpenまたはReject");
                } catch(RuntimeException e) {
                    log.info("通常エラー発生");
                }
            }, 1_000, 1_000, TimeUnit.MILLISECONDS);
        future.get();
        executer.shutdown();
        return "OK";
    }

    @RequestMapping({ "/conf/{percent}" })
    public String conf(@PathVariable long percent) {
        conf.setErrorPercentage(percent);
        return String.format("Set Percent: {}", percent);
    }
}