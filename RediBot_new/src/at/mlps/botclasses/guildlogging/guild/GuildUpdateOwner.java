package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateOwnerEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateOwner extends ListenerAdapter{
	
	public void onGuildUpdateOwner(GuildUpdateOwnerEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
    	eb.setTitle("Guild has been updated");
		eb.setColor(gl.green);
    	eb.setDescription("The guild has now a new owner.");
    	eb.addField("Old Owner:", e.getOldOwner().getAsMention() + " / " + e.getOldOwner().getUser().getName() + "#" + e.getOldOwner().getUser().getDiscriminator(), false);
    	eb.addField("New Owner:", e.getNewOwner().getAsMention() + " / " + e.getNewOwner().getUser().getName() + "#" + e.getNewOwner().getUser().getDiscriminator(), false);
    	eb.setFooter(stime);
    	if(gl.enabledLog(g, "guildupdateowner")) {
    		gl.sendMsg(eb, g);
    	}
	}

}
