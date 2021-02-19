package chatbot;

import java.sql.Date;
import java.text.SimpleDateFormat;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;

public class Log {

	static TextChannel channel;
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

	public Log(String guildid, String channelid, ReadyEvent event) {
		channel = event.getJDA().getGuildById(guildid).getTextChannelById(channelid);
		String user = event.getJDA().getSelfUser().getName();
		send("System", user, "application is online");
	}

	public static void LogMessageReceived(MessageReceivedEvent event) {
		String user = event.getAuthor().getName();
		String msg = shorten(event.getMessage().getContentRaw());
		String link = getLink("@me", event.getChannel().getId(), event.getMessageId());
		String c = "DM";
		if (event.isFromGuild()) {
			c = event.getGuild().getName() + ", " + event.getChannel().getName();
			link = getLink(event.getGuild().getId(), event.getChannel().getId(), event.getMessageId());
		}
		send(c, user, "sent `"+msg+"` ("+link+")");
	}
	public static void LogMessageUpdate(MessageUpdateEvent event) {
		String user = event.getAuthor().getName();
		String msg = shorten(event.getMessage().getContentRaw());
		String link = getLink("@me", event.getChannel().getId(), event.getMessageId());
		String c = "DM";

		if (event.isFromGuild()) {
			c = event.getGuild().getName() + ", " + event.getChannel().getName();
			link = getLink(event.getGuild().getId(), event.getChannel().getId(), event.getMessageId());
		}

		send(c, user, "updated message to `"+msg+"` ("+link+")");
	}
	public static void LogMessageDelete(MessageDeleteEvent event) {
		String link = getLink("@me", event.getChannel().getId(), event.getMessageId());
		String c = "DM";
		
		if (event.isFromGuild()) {
			c = event.getGuild().getName() + ", " + event.getChannel().getName();
			link = getLink(event.getGuild().getId(), event.getChannel().getId(), event.getMessageId());
		}
		send(c, "Anonymous", "deleted a message ("+link+")");

	}

	//functions
	static void send(String tc, String user, String args) {
		channel.sendMessage(getTime() + "("+tc+") <"+user+"> "+args).queue();
	}

	static String getLink(String a, String b, String c) {
		return "<https://discord.com/channels/"+a+"/"+b+"/"+c+">";
	}

	static String shorten(String s) {
		if (s.length() > 100) {
			return s.substring(0, 20) + "...";
		}
		return s;
	}

	static String getTime() {
		return formatter.format(new Date(System.currentTimeMillis()));
	}

}
