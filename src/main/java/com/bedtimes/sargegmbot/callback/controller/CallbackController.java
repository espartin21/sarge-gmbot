package com.bedtimes.sargegmbot.callback.controller;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.messenger.service.MessageParserService;
import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import com.bedtimes.sargegmbot.utils.groupme.service.GetMembersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CallbackController {
	@Value("${groupme.bot.name}")
	private String BOT_NAME;
  
    final MessageSenderService messageSenderService;
    final MessageParserService messageParserService;
    final GetMembersService getMembersService;

    public CallbackController(MessageSenderService messageSenderService, MessageParserService messageParserService, GetMembersService getMembersService) {
        this.messageSenderService = messageSenderService;
        this.messageParserService = messageParserService;
        this.getMembersService = getMembersService;
    }

	@PostMapping("/callback")
	public void callback(@RequestBody CallbackData callbackData) {
        String senderName = callbackData.getName();

        // Check that whoever sent the message isn't us (the bot)
        if (!senderName.equals(BOT_NAME)) {
            String msg = messageParserService.parseMessage(callbackData);
            if (msg != null) {
                System.out.println("Command Response: " + msg);

                ResponseEntity<String> response;
                if (msg.contains("loci")) { // @ALL - msg is actually the attachments variable
                    List<List<String>> members = getMembersService.getMembers();
                    List<List<String>> membersToMention = members.stream().filter(memberInfo -> !memberInfo.get(0).equals(callbackData.getName())).collect(Collectors.toList());
                    List<String> memberNames = membersToMention.stream().map(memberInfo -> "@" + memberInfo.get(0)).collect(Collectors.toList());
                    String mentionString = String.join(" ", memberNames);

                    response = messageSenderService.sendTextMessage("ATTENTION " + mentionString, msg);
                } else { // A normal message
                    response = messageSenderService.sendTextMessage(msg);
                }

                if (response.getStatusCode() != HttpStatus.ACCEPTED) {
                    System.out.println("Following message failed to send: " + msg);
                }
            }
        }
    }
}
