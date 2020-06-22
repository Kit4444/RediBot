package at.mlps.botclasses.guildlogging.role;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.role.update.RoleUpdateNameEvent;

public class RoleUpdateName {
	
	public void onRoleUpdateName(RoleUpdateNameEvent e) {
		GuildLogger gl = new GuildLogger();
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role Update");
		eb.setColor(gl.orange);
		eb.setDescription("Role: ``" + e.getRole().getIdLong() + "``\nOld Name: ``" + e.getOldName() + "``\nNew Name: ``" + e.getNewName() + "``");
		eb.setFooter(stime, g.getIconUrl());
		gl.sendMsg(eb, g);
	}

}
