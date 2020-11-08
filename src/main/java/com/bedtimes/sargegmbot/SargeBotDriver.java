package com.bedtimes.sargegmbot;

import java.util.Dictionary;

import com.bedtimes.sargegmbot.utils.Settings.SettingsDTO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SargeBotDriver {

	public static Dictionary<String, SettingsDTO> settings;

	public static void main(String[] args) {
		SpringApplication.run(SargeBotDriver.class, args);
	}
}
