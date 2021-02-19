package chatbot.moderation;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingRemover extends Moderations{

	@Override
	public String getString() {
		return "@";
	}

	@Override
	public void execute(MessageReceivedEvent event, String args) {
		if (!(event.isFromGuild() && event.getGuild().getId().equals("699278323896811551"))) return;
		
		if (event.getMessage().mentionsEveryone() || event.getMessage().getMentionedMembers().size() == 0)
			return;
		
		event.getMessage().delete().queue();
	}


}
