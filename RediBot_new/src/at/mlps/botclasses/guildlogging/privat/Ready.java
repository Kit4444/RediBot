package at.mlps.botclasses.guildlogging.privat;

import java.awt.Color;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.ReadyEvent;

public class Ready {
	
	public void onReady(ReadyEvent e) {
		EmbedBuilder eb = new EmbedBuilder();
		GuildLogger gl = new GuildLogger();
		eb.setDescription("The bot is now online.");
		eb.setFooter("LOGLEVEL: INFO", "https://redicraft.eu/redianim.gif");
		eb.setColor(Color.green);
		e.getJDA().getGuildById(671772592390144061L).getTextChannelById(709379395428679681L).sendMessage(eb.build()).queue();
		gl.welcCon();
	}

}
