package com.bedtimes.sargegmbot.messenger.service;

import com.bedtimes.sargegmbot.callback.CallbackData;

public interface MessageParserService {
    String parseMessage(CallbackData callbackData);
    // Add your own message parsing function here
}
