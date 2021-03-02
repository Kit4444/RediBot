package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateVerificationLevelEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateVerificationLevel extends ListenerAdapter{
	
	public void onGuildUpdateVerificationLevel(GuildUpdateVerificationLevelEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has been updated");
		eb.setColor(gl.green);
    	eb.setDescription("The guild has now a new verification level.");
    	eb.addField("Old VL:", e.getOldValue().toString(), false);
    	eb.addField("New VL:", e.getNewValue().toString(), false);
    	eb.setFooter(g.getName() + " - " + stime);
    	if(gl.enabledLog(g, "guildupdateverificationlevel")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
