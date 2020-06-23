package at.mlps.botclasses.guildlogging.privat;

import java.awt.Color;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter{
	
	public void onReady(ReadyEvent e) {
		EmbedBuilder eb = new EmbedBuilder();
		GuildLogEvents gl = new GuildLogEvents();
		eb.setDescription("The bot is now online.");
		eb.setFooter("LOGLEVEL: INFO", "https://redicraft.eu/redianim.gif");
		eb.setColor(Color.green);
		e.getJDA().getGuildById(671772592390144061L).getTextChannelById(709379395428679681L).sendMessage(eb.build()).queue();
		gl.welcCon();
	}

}
