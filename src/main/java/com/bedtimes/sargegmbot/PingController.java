package com.bedtimes.sargegmbot;

import com.bedtimes.sargegmbot.messenger.service.MessageSenderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @Autowired
    MessageSenderServiceImpl messageSenderService;

    @Value("${groupme.bot.name}")
    private String bot_name;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Pong!");
    }

    @GetMapping("/")
    public String index() {
        return "Bot in use is " + bot_name;
    }

    @GetMapping("/send")
    public void send() {
        String message = "hello ethan";
        messageSenderService.sendTextMessage(message);
    }
}
