package com.bedtimes.sargegmbot.messenger.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.utils.Command;
import com.bedtimes.sargegmbot.utils.CommandAction;
import com.bedtimes.sargegmbot.utils.Settings.SettingsDTO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class MessageParserServiceImpl implements MessageParserService {
	@Value("${groupme.bot.name}")
	private String BOT_NAME;
	@Value("${groupme.bot.prefix}")
	private String PREFIX;

	private SettingsDTO settings = new SettingsDTO();

	// Command Parser
	private final CommandLineParser parser;
	private final Options options;
	// Options
	protected final Command help = new Command("h", "help", false, "List all commands", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String msg = "Commands:";
			for (Command cmd : Command.registry) {
				msg = msg + "\n-" + cmd.option.getOpt() + "\t→ " + cmd.option.getDescription();
			}
			return msg;
		}
	});

	protected final Command ping = new Command("p", "ping", false, "Pong!", new CommandAction() {
		public String execute(CallbackData callbackData) {
			return "Pong!";
		}
	});

	protected final Command setSettings = new Command("s", "settings", true, "Set the class settings", new CommandAction() {
		public String execute(CallbackData callbackData) {

			return "";
		}
	});

	protected final Command hello = new Command("hello", "hello", true, "Greets a user.", new CommandAction() {
		public String execute(CallbackData callbackData) {
			return "Hello, " + callbackData.getName();
		}
	});

	public MessageParserServiceImpl() {
		options = new Options();
		Command.registry.forEach(x -> options.addOption(x.option));

		parser = new DefaultParser();

		// Print Option Registry
		System.out.println("\n\nREGISTRY: "
				+ Command.registry.stream().map(x -> x.option.getLongOpt()).collect(Collectors.toList()) + "\n\n");
	}

	public String parseMessage(CallbackData callbackData) {
		String msg = callbackData.getText();
		if (!msg.startsWith(PREFIX)) {
			System.out.println("Not a Command [ " + PREFIX + " ]: " + msg);
			return null;
		}
		String out = "";
		try {
			CommandLine cmd = parser.parse(options, msg.substring(PREFIX.length()).split(" "));

			List<String> output = Command.registry.stream().filter(x -> Arrays.asList(cmd.getOptions()).contains(x.option))
					.map(s -> {
						return s.execute(callbackData);
					}).collect(Collectors.toList());

			for (String m : output) {
				out += m + "\n";
			}
			out = out.substring(0, out.length() - 1);
		} catch (Exception e) {
			out = "Try using '" + PREFIX + " --" + help.option.getLongOpt() + "' for more information";
		}
		return out;
	}
}
