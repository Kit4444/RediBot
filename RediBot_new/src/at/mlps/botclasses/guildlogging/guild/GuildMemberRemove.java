package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberRemove extends ListenerAdapter{
	
	static List<User> checkList = new ArrayList<>();
	
	public void onGuildBan(GuildBanEvent e) {
		User user = e.getUser();
		Guild guild = e.getGuild();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
		checkList.add(user);
		
		Executors.newScheduledThreadPool(1).schedule(() -> {
			if(checkList.contains(user)) {
				EmbedBuilder eb = new EmbedBuilder();
				checkList.remove(user);
				eb.setColor(gl.red);
				eb.setTitle("Member banned");
				eb.setThumbnail(user.getAvatarUrl());
				eb.setDescription(user.getName() + "#" + user.getDiscriminator() + " has been banned.");
				eb.setFooter(guild.getName() + " - " + stime, guild.getIconUrl());
				if(gl.enabledLog(guild, "guildban"))
				gl.sendMsg(eb, guild);
			}
		}, 2, TimeUnit.SECONDS);
	}
	
	public void onGuildMemberRemove(GuildMemberRemoveEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(gl.red);
		if(checkList.contains(m.getUser())) {
			checkList.remove(m.getUser());
			eb.setTitle("Member banned and left");
			eb.setThumbnail(m.getUser().getAvatarUrl());
			eb.setDescription(m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " has been banned.\nJoindate: " + gl.retDate(e.getMember().getTimeJoined()));
			StringBuilder sb = new StringBuilder();
			for(Role r : m.getRoles()) {
				sb.append(r.getAsMention());
				sb.append(", ");
			}
			eb.addField("Roles (" + m.getRoles().size() + "):", sb.toString(), false);
			eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
			if(gl.enabledLog(g, "guildban"))
			gl.sendMsg(eb, g);
		}else {
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

}
