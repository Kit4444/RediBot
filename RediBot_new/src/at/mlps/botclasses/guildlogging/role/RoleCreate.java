package at.mlps.botclasses.guildlogging.role;

import java.text.SimpleDateFormat;
import java.util.Date;

import at.mlps.botclasses.guildlogging.guild.GuildLogEvents;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RoleCreate extends ListenerAdapter{
	
	public void onRoleCreate(RoleCreateEvent e) {
		GuildLogEvents gl = new GuildLogEvents();
		Guild g = e.getGuild();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Role has been created.");
        eb.setDescription("Name: ``" + e.getRole().getName() + "``\nID: ``" + e.getRole().getId() + "``\nPermissions: ``" + e.getRole().getPermissionsRaw() + "``");
        eb.setFooter(stime);
		eb.setColor(gl.green);
		if(gl.enabledLog(g, "rolecreate")) {
			gl.sendMsg(eb, g);
		}
	}

}
