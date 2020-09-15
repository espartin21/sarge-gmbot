package com.bedtimes.sargegmbot;

import com.bedtimes.sargegmbot.messenger.MessageSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong!");
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @GetMapping("/send")
    public void send() {
        String message = "hello ethan";
        MessageSender messageSender = new MessageSender();
        messageSender.sendTextMessage(message);
    }
}
