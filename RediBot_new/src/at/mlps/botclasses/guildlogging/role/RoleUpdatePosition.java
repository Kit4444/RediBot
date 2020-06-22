package at.mlps.botclasses.guildlogging.role;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePositionEvent;

public class RoleUpdatePosition {
	
	public void onRoleUpdatePosition(RoleUpdatePositionEvent e) {
		Guild g = e.getGuild();
		GuildLogger gl = new GuildLogger();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role Update");
		eb.setColor(gl.orange);
		eb.setDescription("Role: ``" + e.getRole().getName() + "``\nOld Position: ``" + e.getOldPosition() + "\n``New Position: ``" + e.getNewPosition() + "``");
		eb.setFooter(stime, g.getIconUrl());
		gl.sendMsg(eb, g);
	}

}
