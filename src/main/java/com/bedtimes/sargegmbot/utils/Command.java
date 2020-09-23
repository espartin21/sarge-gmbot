package com.bedtimes.sargegmbot.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.Option;

public class Command {

	public static List<Command> registry = new ArrayList<>();

	public Option option;
	private CommandAction act;

	public Command(String opt, String optLong, boolean hasArg, String description, CommandAction action) {
		option = new Option(opt, optLong, false, description);
		registry.add(this);
		act = action;
	}

	public String execute() {
		return act.execute();
	}
}
