package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberRemove extends ListenerAdapter{
	
	public void onGuildMemberRemove(GuildMemberRemoveEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(gl.red);
		eb.setTitle("Member left");
		eb.setThumbnail(m.getUser().getAvatarUrl());
		eb.setDescription(m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " has left the guild.\nJoindate: " + gl.retDate(e.getMember().getTimeJoined()));
		StringBuilder sb = new StringBuilder();
		for(Role r : m.getRoles()) {
			sb.append(r.getAsMention());
			sb.append(", ");
		}
		eb.addField("Roles (" + m.getRoles().size() + "):", sb.toString(), false);
		eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		if(gl.enabledLog(g, "guildmemberremove"))
		gl.sendMsg(eb, g);
	}

}
