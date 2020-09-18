package com.bedtimes.sargegmbot.callback.controller;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.messenger.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:application-${spring.profiles.active}.properties")
})
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

        // Check that whoever sent the message isn't us (the bot)
        if (callbackData.getName() == BOT_NAME) {
            return;
        }

        ResponseEntity<String> response = messageSenderService.sendTextMessage(callbackData.getText());

        if (response.getStatusCode() != HttpStatus.CREATED) {
            System.out.println("Following message failed to send: " + callbackData.getText());
        }
    }
}
