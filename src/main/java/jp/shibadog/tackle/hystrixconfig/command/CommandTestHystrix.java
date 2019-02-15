package jp.shibadog.tackle.hystrixconfig.command;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.stereotype.Service;

@Service
public class CommandTestHystrix {

    @HystrixCommand(commandKey = "outermost")
    public String outermost(String a) {
        return a;
    }

    public String inside(String a) {
        return insideTo(a);
    }

    @HystrixCommand(commandKey = "inside")
    public String insideTo(String a) {
        return a;
    }
}