package at.mlps.botclasses.guildlogging.privat;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildUnavailableEvent;

public class GuildUnavailable {
	
	public void onGuildUnavailable(GuildUnavailableEvent e) {
		GuildLogger gl = new GuildLogger();
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
