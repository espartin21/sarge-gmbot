package com.bedtimes.sargegmbot.utils.Settings;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class SettingsDTO {
	public String subject;
	public String section;
	public String className;
	public String syllabusURL;
	public Person professor;
	public Person[] ta;

}
