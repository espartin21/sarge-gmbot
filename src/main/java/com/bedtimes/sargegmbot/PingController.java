package com.bedtimes.sargegmbot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/ping")
    public String ping() {
        return "Pong!";
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }
}
