package com.bedtimes.sargegmbot.callback.controller;

import com.bedtimes.sargegmbot.callback.CallbackData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {
    @PostMapping("/callback")
    public void callback(@RequestBody CallbackData body) {
        System.out.println("hello");
    }
}
