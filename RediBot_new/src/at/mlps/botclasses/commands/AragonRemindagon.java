package at.mlps.botclasses.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AragonRemindagon extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		String[] args = e.getMessage().getContentRaw().split(" ");
	}
}