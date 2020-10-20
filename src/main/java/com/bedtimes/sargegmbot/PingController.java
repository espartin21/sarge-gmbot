package com.bedtimes.sargegmbot;

import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import com.bedtimes.sargegmbot.utils.groupme.service.GetMembersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PingController {
    final MessageSenderService messageSenderService;
    final GetMembersService getMembersService;

    public PingController(MessageSenderService messageSenderService, GetMembersService getMembersService) {
        this.messageSenderService = messageSenderService;
        this.getMembersService = getMembersService;
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

    @GetMapping("/members")
    public String members() {
        String x = "";
        List<String> members = getMembersService.getMembers();
        for (String member : members) {
            x += member;
        }

        return x;
    }
}
