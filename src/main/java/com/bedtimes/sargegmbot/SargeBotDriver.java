package com.bedtimes.sargegmbot;

import java.util.HashMap;
import java.util.Map;

import com.bedtimes.sargegmbot.utils.Settings.SettingsDTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SargeBotDriver {

	public static Map<String, SettingsDTO> settings;

	public static void main(String[] args) {
		settings = new HashMap<String, SettingsDTO>();
		SpringApplication.run(SargeBotDriver.class, args);
	}
}
