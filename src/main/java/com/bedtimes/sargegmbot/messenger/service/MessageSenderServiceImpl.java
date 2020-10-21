package com.bedtimes.sargegmbot.messenger.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource("classpath:application-${spring.profiles.active}.properties")
})
@Service
public class MessageSenderServiceImpl implements MessageSenderService {
    @Value("${groupme.bot.id}")
    private String BOT_ID;

    @Value("${groupme.post-url}")
    private String POST_URL;

    public ResponseEntity<String> sendTextMessage(String msg) {
        return sendTextMessage(msg, "");
    }

    public ResponseEntity<String> sendTextMessage(String msg, String attachments) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String json = "{\"bot_id\": \"" + BOT_ID + "\", \"text\": \"" + msg + "\", \"attachments\": " + attachments + "}";

        HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(POST_URL, request, String.class);
    }
}
