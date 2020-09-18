package com.bedtimes.sargegmbot.messenger.service;

import org.springframework.http.ResponseEntity;

public interface MessageSenderService {
    ResponseEntity<String> sendTextMessage(String msg);
}
