package at.mlps.botclasses.guildlogging.privat;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildLeave extends ListenerAdapter{
	
	public void onGuildLeave(GuildLeaveEvent e) {
		GuildLogEvents gl = new GuildLogEvents();
		Guild g = e.getJDA().getGuildById(gl.rediassetg);
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
    	eb.setTitle("Bot has left guild");
    	eb.setColor(gl.red);
    	eb.addField("Guildname:", e.getGuild().getName(), false);
    	eb.addField("Members:", e.getGuild().getMembers().size() + " / 250.000", false);
    	eb.addField("Boostcount:", e.getGuild().getBoostCount() + " with " + e.getGuild().getBoosters().size(), false);
    	eb.addField("Cat/Text/Voice", e.getGuild().getCategories().size() + " / " + e.getGuild().getTextChannels().size() + " / " + e.getGuild().getVoiceChannels().size(), false);
    	eb.setThumbnail(e.getGuild().getIconUrl());
    	eb.addField("Roles:", e.getGuild().getRoles().size() + "", false);
    	eb.setFooter(stime);
        if(g.getIdLong() == gl.rediassetg) {
        	g.getTextChannelById(gl.rediassetlog).sendMessage(eb.build()).queue();
        }
	}

}
