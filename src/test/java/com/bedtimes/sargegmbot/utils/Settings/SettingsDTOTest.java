package com.bedtimes.sargegmbot.utils.Settings;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

public class SettingsDTOTest {
	@Test
	public void testSerialization() throws JsonProcessingException {
		Person p0 = new Person("Jack P.", "jack@tamu.edu", "HULL", "MWF 2:30");
		Person p1 = new Person("John Smith", "john@tamu.edu", "ABP", "MWF 2:30");
		Person p2 = new Person("Cecil H.", "BigGuy@tamu.edu", "KGHM", "MWF 2:30");

		SettingsDTO s = new SettingsDTO("CSCE", "464", "Computers & New Media", "TBD", "TBD", "link", p0, new Person[] { p1, p2 });

		ObjectMapper objectMapper = new ObjectMapper();

		String str = objectMapper.writeValueAsString(s);
		System.out.println(str);

		SettingsDTO s1 = objectMapper.readValue(str, SettingsDTO.class);
		System.out.println(s1);
	}
}
