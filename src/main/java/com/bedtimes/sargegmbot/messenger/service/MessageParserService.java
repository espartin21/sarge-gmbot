package com.bedtimes.sargegmbot.messenger.service;

import org.springframework.stereotype.Service;

@Service
public interface MessageParserService {
    String parseMessage(String msg);
    // Add your own message parsing function here
}
