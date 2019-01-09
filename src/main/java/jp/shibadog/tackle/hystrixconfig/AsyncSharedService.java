package jp.shibadog.tackle.hystrixconfig;

import java.util.function.Consumer;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AsyncSharedService {

    @Async
    public void async(Consumer<Integer> fnc, int index) {
        
        long start = System.currentTimeMillis();
        log.info("{}本目->うごけぇぇぇぇぇぇ！", index);
        fnc.accept(index);
        long end = System.currentTimeMillis();
        log.info("{}本目->おわたぁぁぁぁぁぁ！: {}ms", index, (end - start));
    }
}