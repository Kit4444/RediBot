package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.update.GuildUpdateIconEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildUpdateIcon extends ListenerAdapter{
	
	public void onGuildUpdateIcon(GuildUpdateIconEvent e) {
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
		eb.setTitle("Click me if you don't see the avatar", e.getNewIconUrl());
		eb.setDescription("Guild Icon has been updated.");
		eb.setImage(e.getNewIconUrl());
		eb.setColor(gl.green);
		eb.setFooter(g.getName() + " - " + stime);
		if(gl.enabledLog(g, "guildupdateicon")) {
			gl.sendMsg(eb, g);
		}
	}

}
