package at.mlps.botclasses.commands;

import java.io.IOException;
import java.util.List;

import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordSugg_Voter extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		TextChannel chan = e.getChannel();
		Guild g = e.getGuild();
		YamlFile cfg = new YamlFile("configs/guildsettings.yml");
		try {
			cfg.load();
		} catch (InvalidConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		if(cfg.contains("SuggestionVoter.list." + g.getIdLong())) {
			List<Long> channels = cfg.getLongList("SuggestionVoter.list." + g.getIdLong());
			if(channels.contains(chan.getIdLong())) {
				if(cfg.contains("SuggestionVoter.reaction." + g.getIdLong())) {
					int reactmode = cfg.getInt("SuggestionVoter.reaction." + g.getIdLong());
					boolean threading = false;
					if(cfg.contains("SuggestionVoter.threading." + g.getIdLong() + "." + chan.getIdLong())) {
						threading = cfg.getBoolean("SuggestionVoter.threading." + g.getIdLong() + "." + chan.getIdLong());
					}// if threading = true, a thread should be created with the origin message.
					switch(reactmode) {
					case 0: e.getMessage().addReaction("upvote:671772876474679326").queue();
					e.getMessage().addReaction("neutralvote:671772876453707776").queue();
					e.getMessage().addReaction("downvote:671772876432605204").queue();
					break;
					case 1: e.getMessage().addReaction("upvote:671772876474679326").queue();
					e.getMessage().addReaction("downvote:671772876432605204").queue();
					break;
					case 2: e.getMessage().addReaction("plus:836638630109118474").queue();
					e.getMessage().addReaction("minus:836638629978701894").queue(); 
					break;
					}
				}else {
					e.getMessage().addReaction("upvote:671772876474679326").queue();
					e.getMessage().addReaction("neutralvote:671772876453707776").queue();
					e.getMessage().addReaction("downvote:671772876432605204").queue();
				}
			}
		}
	}
}