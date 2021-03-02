package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateName extends ListenerAdapter{
	
	public void onGuildUpdateName(GuildUpdateNameEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has been updated");
		eb.setColor(gl.green);
    	eb.setDescription("The guild has now a new name.");
    	eb.addField("Old Name:", e.getOldName(), false);
    	eb.addField("New Name:", e.getNewName(), false);
    	eb.setFooter(g.getName() + " - " + stime);
    	if(gl.enabledLog(g, "guildupdatename")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
