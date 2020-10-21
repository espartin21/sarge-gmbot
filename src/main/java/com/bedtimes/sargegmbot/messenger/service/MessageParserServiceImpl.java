package com.bedtimes.sargegmbot.messenger.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.utils.Command;
import com.bedtimes.sargegmbot.utils.CommandAction;
import com.bedtimes.sargegmbot.utils.Settings.Person;
import com.bedtimes.sargegmbot.utils.Settings.SettingsDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

	private SettingsDTO settings = null;

	// Command Parser
	private final CommandLineParser parser;
	private final Options options;
	// Options
	protected final Command help = new Command("h", "help", false, "List all commands", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String msg = "Commands: ";
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
			ObjectMapper om = new ObjectMapper();
			//TODO Save Settings somewhere on disk
			try {
				settings = om.readValue(callbackData.getText().substring(PREFIX.length() + 4), SettingsDTO.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return "Malformed Settings";
			}
			return "Settings Set";
		}
	});

	protected final Command getClassInfo = new Command("c", "class", false, "Gets a Class's information", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String dat = "No Information Available";
			if(settings != null){
				dat = "Class: " + settings.getSubject() + " " + settings.getSection();
				dat += "\nName: " + settings.getClassName();
				dat += "\nSyllabus: " + settings.getSyllabusURL();
			}
			return dat;
		}
	});

	protected final Command getProfInfo = new Command("prof", "professor", false, "Gets a Professor's information", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String dat = "No Information Available";
			if(settings != null){
				dat = "Professor: " + settings.getProfessor().getName();
				dat += "\nemail: " + settings.getProfessor().getEmail();
				dat += "\nOffice: " + settings.getProfessor().getOffice();
				dat += "\nOffice Hours: " + settings.getProfessor().getOfficeHours();
			}
			return dat;
		}
	});

	protected final Command getTAInfo = new Command("ta", "ta", false, "Gets a TA's information", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String dat = "No Information Available";
			if(settings != null){
				dat = "";
				for (Person p : settings.getTa()) {
					dat =  "\nTA: " + p.getName();
					dat += "\nemail: " + p.getEmail();
					dat += "\nOffice: " + p.getOffice();
					dat += "\nOffice Hours: " + p.getOfficeHours();
				}
				dat = dat.substring(1);
			}
			return dat;
		}
	});

	protected final Command hello = new Command("hello", "hello", false, "Greets a user.", new CommandAction() {
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
