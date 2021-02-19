package chatbot.commands.util;

import chatbot.Bot;
import chatbot.commands.Commands;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Osu extends Commands{

	@Override
	public String name() {
		return "pp";
	}

	@Override
	public String description() {
		return "hello and welcome to os lets learn how to play";
	}

	@Override
	public String usage() {
		return Bot.prefix+name()+" <osu username>";
	}

	@Override
	public int cooldown() {
		return 0;
	}

	@Override
	public void execute(MessageReceivedEvent event, String[] args) {
		
		
	}
	

}
