package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateBoostCountEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateBoostCount extends ListenerAdapter{
	
	public void onGuildUpdateBoostCount(GuildUpdateBoostCountEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("New Boost Count");
		eb.setColor(gl.green);
    	eb.setDescription("The Boostcount has been changed.");
    	eb.addField("Old Count", e.getOldBoostCount() + "", false);
    	eb.addField("New Count", e.getNewBoostCount() + "", false);
    	eb.setFooter(stime);
    	if(gl.enabledLog(g, "guildupdateboostcount")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
