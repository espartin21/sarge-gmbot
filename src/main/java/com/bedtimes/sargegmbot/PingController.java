package com.bedtimes.sargegmbot;

import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    final MessageSenderService messageSenderService;

    public PingController(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong!");
    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }

    @PostMapping("/send")
    public void send() {
        String message = "hello ethan";
        messageSenderService.sendTextMessage(message);
    }
}
