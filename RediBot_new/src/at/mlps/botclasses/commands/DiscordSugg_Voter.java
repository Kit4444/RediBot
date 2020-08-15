package at.mlps.botclasses.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordSugg_Voter extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		TextChannel chan = e.getChannel();
		Guild g = e.getGuild();
		if(g.getIdLong() == 548136727697555496L) {
			if(chan.getIdLong() == 551709303485104128L) {
				e.getMessage().addReaction("upvote:671772876474679326").queue();
				e.getMessage().addReaction("neutralvote:671772876453707776").queue();
				e.getMessage().addReaction("downvote:671772876432605204").queue();
			}
		}
	}

}
