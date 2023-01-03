package at.mlps.chatleveling;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LevelingActor extends ListenerAdapter{
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		Guild guild = e.getGuild();
		User user = e.getAuthor();
	}
	
	
}