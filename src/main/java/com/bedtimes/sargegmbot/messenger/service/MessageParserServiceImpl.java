package com.bedtimes.sargegmbot.messenger.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageParserServiceImpl implements MessageParserService {
	@Value("${groupme.bot.name}")
	private String BOT_NAME;

	public String parseMessage(String msg) {
		List<String> tokens = Arrays.asList(msg.split(" "));
		System.out.println(tokens);

		if(tokens.stream().filter( x -> x.equals("@" + BOT_NAME)).count() > 0){
			System.out.println("Bot has been mentioned.");




		}
		return "";
	}

	// Add the same function you made in MessageParserService here and make it
	// private
}
