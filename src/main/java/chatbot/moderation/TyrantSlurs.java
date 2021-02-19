package chatbot.moderation;

import java.util.LinkedList;
import java.util.List;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TyrantSlurs extends Moderations{

	@Override
	public String getString() {
		return " dyno ";
	}
	
	String criminalName = "eurasian war criminal";
	int criminalOrder = 0;
	String citizenName = "citizen of oceania";
	int citizenOrder = 1;
	
	@Override
	public void execute(MessageReceivedEvent event, String args) {
		if (!(event.isFromGuild() && event.getGuild().getId().equals("699278323896811551"))) return;

		Guild g = event.getMember().getGuild();
		
		Member bot = g.getMember(event.getJDA().getSelfUser());
		Member member = event.getMember();
		List<Role> roles = new LinkedList<Role>();
		
		Role criminal = g.getRolesByName(criminalName, true).get(criminalOrder);
		Role citizen = g.getRolesByName(citizenName, true).get(citizenOrder);

		roles.add(criminal);
		roles.add(citizen);
		
		event.getChannel().sendMessage("THOUGHT CRIME").queue();
		
		if (!canInteract(bot, member, roles))
			return;
		
		
		
		g.modifyMemberRoles(member, roles).queue();
		
	}

	boolean canInteract(Member bot, Member member, List<Role> roles) {
		if (!bot.canInteract(member)) 
			return false;
		
		for (Role r : member.getRoles()) {
			if (!bot.canInteract(r))
				return false;
		}
		
		for (Role r : roles) {
			if (!bot.canInteract(r))
				return false;
		}
		
		return true;
		
	}
}
