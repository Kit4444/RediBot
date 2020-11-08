package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateExplicitContentLevelEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateExplicitContent extends ListenerAdapter{

	public void onGuildUpdateExplicitContentLevel(GuildUpdateExplicitContentLevelEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has been updated");
		eb.setColor(gl.green);
    	eb.setDescription("The guild has now a new explicit content level.");
    	eb.addField("Old ECL:", e.getOldLevel().getDescription(), false);
    	eb.addField("New ECL:", e.getNewLevel().getDescription(), false);
    	eb.setFooter(stime);
    	if(gl.enabledLog(g, "guildupdateexplicitcontentlevel")) {
    		gl.sendMsg(eb, g);
    	}
	}
}
