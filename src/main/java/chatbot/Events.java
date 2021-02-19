package chatbot;

import chatbot.moderation.Moderations;
import chatbot.reactions.Reaction;

import javax.annotation.Nonnull;

import chatbot.Log;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Events extends ListenerAdapter{

	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		Message msg = event.retrieveMessage().complete();

		if (msg.getAuthor().isBot() || !event.getReaction().getReactionEmote().isEmoji()) return;

		String c = event.getReaction().getReactionEmote().getEmoji();
		for (Reaction r : Bot.reactions.values()) 
			if (r.types().contains(c))
				r.execute(event, msg);
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.getAuthor().isBot()) return;

		Log.LogMessageReceived(event);

		Message msg = event.getMessage();
		String msgRaw = msg.getContentRaw().toLowerCase();
		String msgClean = msg.getContentRaw().toLowerCase().replaceAll("[^a-z ]", " ");
		String[] split = msgRaw.split("( +)");
		//commands
		if (split[0].startsWith(Bot.getPrefix()) && Bot.commands.containsKey(split[0])) {
			try { Bot.commands.get(split[0]).execute(event, split);
			} catch (Exception e) {
				event.getChannel().sendMessage("Error: " + e.getMessage()).queue();
			}
		}

		//moderation
		for (Moderations m : Bot.moderations.values())	//alright stop; this is a complete clusterfuck; how did this get so out of control
			if ((" "+msgClean+" ").contains(m.getString()) || msgRaw.contains(m.getString())) 
				m.execute(event, msgRaw);


	}

	@Override
	public void onReady(ReadyEvent event) {
		new Log("[guildid#]", "[channelid#]", event);	//telescreen in shid!gang
	}
	@Override
	public void onMessageUpdate(MessageUpdateEvent event) {
		if (event.getAuthor().isBot()) return;
		Log.LogMessageUpdate(event);
	}
	@Override
	public void onMessageDelete(@Nonnull MessageDeleteEvent event) {
		Log.LogMessageDelete(event);
	}
	@Override
	public void onGuildBan(@Nonnull GuildBanEvent event) {

	}
	@Override
	public void onGuildUnban(@Nonnull GuildUnbanEvent event) {

	}




}
