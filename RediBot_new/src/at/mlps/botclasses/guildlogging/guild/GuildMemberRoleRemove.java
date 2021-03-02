package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberRoleRemove extends ListenerAdapter{
	
	public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Role remove");
		eb.setColor(gl.orange);
		if(e.getRoles().size() == 1) {
			eb.setDescription("User: " + m.getAsMention() + "\nRole removed: " + e.getRoles().get(0).getName());
		}else {
			StringBuilder sb = new StringBuilder();
			for(Role r : e.getRoles()) {
				sb.append(r.getName());
				sb.append(", ");
			}
			eb.setDescription("User: " + m.getAsMention() + "\nRoles removed: " + sb.toString());
		}
		eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		if(gl.enabledLog(g, "guildmemberroleremove")) {
			gl.sendMsg(eb, g);
		}
	}

}
