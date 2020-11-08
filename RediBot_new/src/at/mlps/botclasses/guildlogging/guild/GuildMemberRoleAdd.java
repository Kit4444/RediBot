package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberRoleAdd extends ListenerAdapter{
	
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
        GuildLogEvents gl = new GuildLogEvents();
		eb.setTitle("Role add");
		eb.setColor(gl.orange);
		if(e.getRoles().size() == 1) {
			eb.setDescription("User: " + m.getAsMention() + "\nRole added: " + e.getRoles().get(0).getName());
		}else {
			StringBuilder sb = new StringBuilder();
			for(Role r : e.getRoles()) {
				sb.append(r.getName());
				sb.append(", ");
			}
			eb.setDescription("User: " + m.getAsMention() + "\nRoles added: " + sb.toString());
		}
		eb.setFooter(stime, g.getIconUrl());
		if(g.getIdLong() == gl.redimain) {
			Role guest = e.getGuild().getRoleById(651569972920713226L);
			Role player = e.getGuild().getRoleById(548175925901000734L);
			if(m.getRoles().contains(player)) {
				g.removeRoleFromMember(m, guest).complete();
			}
		}
		if(gl.enabledLog(g, "guildmemberroleadd")) {
			gl.sendMsg(eb, g);
		}
	}

}
