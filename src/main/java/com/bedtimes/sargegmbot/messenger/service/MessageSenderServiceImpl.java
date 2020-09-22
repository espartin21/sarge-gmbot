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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("bot_id", BOT_ID);
        map.add("text", msg);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.postForEntity(POST_URL, request, String.class);
    }
}
