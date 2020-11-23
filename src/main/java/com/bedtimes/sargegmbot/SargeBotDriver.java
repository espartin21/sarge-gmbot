package com.bedtimes.sargegmbot;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;

import com.bedtimes.sargegmbot.utils.Settings.SettingsDTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SargeBotDriver {
	public static Map<String, SettingsDTO> settings;
	public static Map<String, ArrayList<String>> pinnedMessages;
	public static Map<String, Stack<String>> previousMessages;
	
	public static void main(String[] args) {
		settings = new HashMap<String, SettingsDTO>();
		pinnedMessages = new HashMap<String, ArrayList<String>>();
		previousMessages = new HashMap<String, Stack<String>>();

		SpringApplication.run(SargeBotDriver.class, args);
	}
}
