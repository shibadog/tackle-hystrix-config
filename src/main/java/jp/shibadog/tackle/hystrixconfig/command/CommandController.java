package jp.shibadog.tackle.hystrixconfig.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/command")
public class CommandController {
    @Autowired
    private CommandTestHystrix service;

    @RequestMapping({"/outside"})
    public String outside() {
        service.outermost("aaa");
        return "OK";
    }
    
    @RequestMapping({"/inside"})
    public String inside() {
        service.inside("aaa");
        return "OK";
    }
    
    @RequestMapping({"/insideTo"})
    public String insideTo() {
        service.insideTo("aaa");
        return "OK";
    }
}