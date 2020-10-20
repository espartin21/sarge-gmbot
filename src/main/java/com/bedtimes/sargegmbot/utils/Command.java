package com.bedtimes.sargegmbot.utils;

import java.util.ArrayList;
import java.util.List;

import com.bedtimes.sargegmbot.callback.CallbackData;
import org.apache.commons.cli.Option;

public class Command {

	public static List<Command> registry = new ArrayList<>();
	public Option option;
	private final CommandAction act;

	public Command(String opt, String optLong, boolean hasArg, String description,  CommandAction action) {
		option = new Option(opt, optLong, hasArg, description);
		act = action;
		registry.add(this);
	}

	public String execute(CallbackData callbackData, String... args) {
		return act.execute(callbackData, args);
	}
}
