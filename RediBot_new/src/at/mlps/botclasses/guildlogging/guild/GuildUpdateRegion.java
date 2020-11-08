package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateRegionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateRegion extends ListenerAdapter{
	
	public void onGuildUpdateRegion(GuildUpdateRegionEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has been updated");
		eb.setColor(gl.green);
    	eb.setDescription("The guild is now in a new region.");
    	eb.addField("Old Region:", e.getOldRegion().getName(), false);
    	eb.addField("New Region:", e.getNewRegion().getName(), false);
    	eb.setFooter(stime);
    	if(gl.enabledLog(g, "guildupdateregion")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
