package com.bedtimes.sargegmbot.callback.controller;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController {
    @Value("${groupme.bot.name")
    private String BOT_NAME;

    final MessageSenderService messageSenderService;

    public CallbackController(MessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
    }

    @PostMapping("/callback")
    public void callback(@RequestBody CallbackData callbackData) {
        System.out.println(callbackData.getName() + ": " + callbackData.getText());

        String senderName = callbackData.getName();

        // Check that whoever sent the message isn't us (the bot)
        if (!senderName.equals(BOT_NAME)) {
            System.out.println(senderName + "and " + BOT_NAME);

            ResponseEntity<String> response = messageSenderService.sendTextMessage(callbackData.getText());

            if (response.getStatusCode() != HttpStatus.ACCEPTED) {
                System.out.println("Following message failed to send: " + callbackData.getText());
            }
        }
    }
}
