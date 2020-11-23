package com.bedtimes.sargegmbot.messenger.service;

import java.util.stream.Collectors;
import java.util.*;

import com.bedtimes.sargegmbot.SargeBotDriver;
import com.bedtimes.sargegmbot.callback.CallbackData;
import com.bedtimes.sargegmbot.mention.service.MentionAllService;
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


	final MentionAllService mentionAllService;

	// Command Parser
	private final CommandLineParser parser;
	private final Options options;
	// Options
	protected final Command help = new Command("h", "help", false, "List all commands", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String msg = "Commands: ";
			for (Command cmd : Command.registry) {
				msg = msg + "\\n-" + cmd.option.getOpt() + "\\t→ " + cmd.option.getDescription();
			}
			return msg;
		}
	});

	protected final Command ping = new Command("p", "ping", false, "Pong!", new CommandAction() {
		public String execute(CallbackData callbackData) {
			return "Pong!";
		}
	});

	protected final Command addNewPin = new Command("pin", "pinMessage", false, "Add a the last message to the list of pinned messages", new CommandAction() {
		public String execute(CallbackData callbackData) {
			if (SargeBotDriver.pinnedMessages == null) {
				SargeBotDriver.pinnedMessages = new HashMap<String, ArrayList<String>>(); 
			}

			if (SargeBotDriver.pinnedMessages.containsKey(callbackData.getGroup_id())) {
				SargeBotDriver.pinnedMessages.get(callbackData.getGroup_id()).add(SargeBotDriver.previousMessages.get(callbackData.getGroup_id()).lastElement());

			} else {
				if (SargeBotDriver.previousMessages == null) {
					SargeBotDriver.previousMessages = new HashMap<String, Stack<String>>(); 
				}

				if (SargeBotDriver.previousMessages.containsKey(callbackData.getGroup_id())) {
					ArrayList<String> p = new ArrayList<String>();
					p.add(SargeBotDriver.previousMessages.get(callbackData.getGroup_id()).lastElement());

					SargeBotDriver.pinnedMessages.put(callbackData.getGroup_id(), p);

				} else {
					return "No messages to pin";
				}
			}

			return "Last message added to list of pins";
		}
	});

	protected final Command showPinnedMessages = new Command("sp", "showPins", false, "Show all current pinned messages", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String pinnedMesses = "No Pinned Messages";

			try {
				ArrayList<String> p = SargeBotDriver.pinnedMessages.get(callbackData.getGroup_id());
				if (p.size() > 0) {
					pinnedMesses = "Pinned Messages: ";
				}

				for (String str : p) {
					pinnedMesses += "\\n" + str;
				}

			} catch(NullPointerException e){}
				return pinnedMesses;
		}
	});

	protected final Command setSettings = new Command("s", "settings", true, "Set the class settings", new CommandAction() {
		public String execute(CallbackData callbackData) {
			ObjectMapper om = new ObjectMapper();
			//TODO Save Settings somewhere on disk
			try {
				SargeBotDriver.settings.put(callbackData.getGroup_id(), om.readValue(callbackData.getText().substring(PREFIX.length() + 4), SettingsDTO.class));
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
			try{
				SettingsDTO s = SargeBotDriver.settings.get(callbackData.getGroup_id());
				dat = "Class: " + s.getSubject() + " " + s.getSection();
				dat += "\\nName: " + s.getClassName();
				dat += "\\nSyllabus: " + s.getSyllabusURL();
			}catch(NullPointerException e){}
			return dat;
		}
	});

	protected final Command getProfInfo = new Command("prof", "professor", false, "Gets a Professor's information", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String dat = "";
			try{
				SettingsDTO s = SargeBotDriver.settings.get(callbackData.getGroup_id());
				dat = "Professor: " + s.getProfessor().getName();
				dat += "\\nemail: " + s.getProfessor().getEmail();
				dat += "\\nOffice: " + s.getProfessor().getOffice();
				dat += "\\nOffice Hours: " + s.getProfessor().getOfficeHours();
			}catch(NullPointerException e){dat = "No Information Available";}
			return dat;
		}
	});

	protected final Command getTAInfo = new Command("ta", "ta", false, "Gets a TA's information", new CommandAction() {
		public String execute(CallbackData callbackData) {
			String dat = "";
			try{
				SettingsDTO s = SargeBotDriver.settings.get(callbackData.getGroup_id());
				for (Person p : s.getTa()) {
					dat =  "\\nTA: " + p.getName();
					dat += "\\nemail: " + p.getEmail();
					dat += "\\nOffice: " + p.getOffice();
					dat += "\\nOffice Hours: " + p.getOfficeHours();
				}
				dat = dat.substring(2);
			}catch(NullPointerException e){ dat = "No Information Available";}
			return dat;
		}
	});

	protected final Command hello = new Command("hello", "hello", false, "Greets a user.", new CommandAction() {
		public String execute(CallbackData callbackData) {
			return "Hello, " + callbackData.getName();
		}
	});

	protected final Command all = new Command("a", "all", false, "Mention everyone", new CommandAction() {
		@Override
		public String execute(CallbackData callbackData) {
			return mentionAllService.mentionAll(callbackData);
		}
	});

	public MessageParserServiceImpl(MentionAllService mentionAllService) {
		this.mentionAllService = mentionAllService;

		options = new Options();
		Command.registry.forEach(x -> options.addOption(x.option));

		parser = new DefaultParser();

		// Print Option Registry
		System.out.println("\\n\\nREGISTRY: "
				+ Command.registry.stream().map(x -> x.option.getLongOpt()).collect(Collectors.toList()) + "\\n\\n");
	}

	public String parseMessage(CallbackData callbackData) {
		String msg = callbackData.getText();

		if (!msg.startsWith(PREFIX)) {
			System.out.println("Not a Command [ " + PREFIX + " ]: " + msg);

			if (SargeBotDriver.previousMessages == null) {
				SargeBotDriver.previousMessages = new HashMap<String, Stack<String>>(); 
			}

			if (SargeBotDriver.previousMessages.containsKey(callbackData.getGroup_id())) {
				SargeBotDriver.previousMessages.get(callbackData.getGroup_id()).add("'" + callbackData.getText() + "'" + " - " + callbackData.getName());

				Stack<String> p = SargeBotDriver.previousMessages.get(callbackData.getGroup_id());

				if (p.size() > 15) {
					p.remove(0);
				}

			} else {
				Stack<String> p = new Stack<String>();
				p.add("'" + callbackData.getText() + "'" + " - " + callbackData.getName());

				SargeBotDriver.previousMessages.put(callbackData.getGroup_id(), p);
			}

       		System.out.println("The new stack is: " + SargeBotDriver.previousMessages.get(callbackData.getGroup_id())); 

			return null;
		}

		String output;
		try {
			CommandLine cmd = parser.parse(options, msg.substring(PREFIX.length()).split(" "));

			output = Command.registry.stream().filter(x -> x.option.equals(cmd.getOptions()[0]))
					.map(s -> s.execute(callbackData)).collect(Collectors.joining());

		} catch (Exception e) {
			output = "Try using '" + PREFIX + " --" + help.option.getLongOpt() + "' for more information";
		}

		return output;
	}
}
