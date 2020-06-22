package at.mlps.botclasses.guildlogging.role;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.role.update.RoleUpdateMentionableEvent;

public class RoleUpdateMentionable {
	
	public void onRoleUpdateMentionable(RoleUpdateMentionableEvent e) {
		Guild g = e.getGuild();
		GuildLogger gl = new GuildLogger();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role Update");
		eb.setColor(gl.orange);
		eb.setDescription("Role: ``" + e.getRole().getName() + "``\nMentionable: ``" + e.getNewValue() + "``");
		eb.setFooter(stime, g.getIconUrl());
		gl.sendMsg(eb, g);
	}

}
