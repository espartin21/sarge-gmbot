package com.bedtimes.sargegmbot.messenger.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bedtimes.sargegmbot.utils.Command;
import com.bedtimes.sargegmbot.utils.CommandAction;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageParserServiceImpl implements MessageParserService {
	@Value("${groupme.bot.name}")
	private String BOT_NAME;
	@Value("${groupme.bot.prefix}")
	private String PREFIX;

	// Command Parser
	private CommandLineParser parser;
	private Options options;

	// Options
	private Command help = new Command("h", "help", false, "List all commands", new CommandAction() {
		public String execute() {
			String msg = "Commands:";
			for (Command cmd : Command.registry) {
				msg = msg + "\n-" + cmd.option.getOpt() + "\t→ " + cmd.option.getDescription();
			}
			return msg;
		}
	});

	@SuppressWarnings("unused")
	private Command ping = new Command("p", "ping", false, "Pong!", new CommandAction() {
		public String execute() {
			return "Pong!";
		}
	});

	public MessageParserServiceImpl() {
		options = new Options();
		Command.registry.stream().forEach(x -> options.addOption(x.option));

		parser = new DefaultParser();

		// Print Option Registry
		System.out.println("\n\nREGISTRY: "
				+ Command.registry.stream().map(x -> x.option.getLongOpt()).collect(Collectors.toList()) + "\n\n");
	}

	public String parseMessage(String msg) {
		if (!msg.substring(0, PREFIX.length()).equals(PREFIX)) {
			System.out.println("Not a Command [ " + PREFIX + " ]: " + msg);
			return null;
		}
		String out = "";
		try {
			CommandLine cmd = parser.parse(options, msg.substring(PREFIX.length()).split(" "));

			List<String> output = Command.registry.stream().filter(x -> Arrays.asList(cmd.getOptions()).contains(x.option))
					.map(s -> {
						return s.execute();
					}).collect(Collectors.toList());

			for (String m : output) {
				out += out + m + "\n";
			}
			out = out.substring(0, out.length() - 1);
		} catch (Exception e) {
			out = "Try using '" + PREFIX + " --" + help.option.getLongOpt() + "' for more information";
		}
		return out;
	}
}
