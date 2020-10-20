package com.bedtimes.sargegmbot.utils.Settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Person {
	public String name;
	public String email;
	public String office;
	public String officeHours;
}
