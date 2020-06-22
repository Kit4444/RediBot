package at.mlps.botclasses.guildlogging.role;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.GuildLogger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.role.update.RoleUpdateColorEvent;

public class RoleUpdateColor {
	
	public void onRoleUpdateColor(RoleUpdateColorEvent e) {
		GuildLogger gl = new GuildLogger();
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role Update");
		eb.setColor(e.getNewColor());
		eb.setDescription("Role: ``" + e.getRole().getName() + "`` \nOld Color: ``" + gl.hexCol(e.getOldColorRaw()) + "``\nNew Color: ``" + gl.hexCol(e.getNewColorRaw()) + "``");
		eb.setFooter(stime, g.getIconUrl());
		gl.sendMsg(eb, g);
	}

}
