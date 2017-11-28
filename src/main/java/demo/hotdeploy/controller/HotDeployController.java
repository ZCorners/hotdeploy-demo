package demo.hotdeploy.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HotDeployController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello, hot deploy, hello, hello";
    }
}
