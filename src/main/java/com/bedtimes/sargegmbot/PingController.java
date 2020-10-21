package com.bedtimes.sargegmbot;

import com.bedtimes.sargegmbot.mention.service.MentionAllService;
import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import com.bedtimes.sargegmbot.utils.groupme.service.GetMembersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PingController {
    final MessageSenderService messageSenderService;
    final GetMembersService getMembersService;
    final MentionAllService mentionAllService;

    public PingController(MessageSenderService messageSenderService, GetMembersService getMembersService, MentionAllService mentionAllService) {
        this.messageSenderService = messageSenderService;
        this.getMembersService = getMembersService;
        this.mentionAllService = mentionAllService;
    }

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
        String message = "hey @Ethan Partin";
        String attachments = "[{\"loci\": [[4, 13]], \"type\": \"mentions\", \"user_ids\": [41807887]}]";
        messageSenderService.sendTextMessage(message, attachments);
    }

    @GetMapping("/members")
    public String members() {
        String x = "";
        List<List<String>> members = getMembersService.getMembers();
        for (List<String> memberInfo : members) {
            x += memberInfo.get(0);
        }

        return x;
    }

//    @GetMapping("/mention")
//    public void mention() {
//        List<List<String>> members = getMembersService.getMembers();
//        mentionAllService.createMentions(members, "hi");
//    }
}
