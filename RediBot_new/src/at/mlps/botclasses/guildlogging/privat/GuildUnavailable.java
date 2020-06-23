package at.mlps.botclasses.guildlogging.privat;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildUnavailableEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUnavailable extends ListenerAdapter{
	
	public void onGuildUnavailable(GuildUnavailableEvent e) {
		GuildLogEvents gl = new GuildLogEvents();
		Guild g = e.getJDA().getGuildById(gl.rediassetg);
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        if(g.getIdLong() == gl.rediassetg) {
        	EmbedBuilder eb = new EmbedBuilder();
        	eb.setFooter(stime);
        	eb.setColor(gl.darkred);
        	eb.setTitle("Guild is unavailable!");
        	eb.setDescription("Guildname: " + e.getGuild().getName() + "\nGuildid: " + e.getGuild().getIdLong());
        	g.getTextChannelById(gl.rediassetlog).sendMessage(eb.build()).queue();
        }
	}

}
