package at.mlps.botclasses.guildlogging.guild;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberUpdateNickname extends ListenerAdapter{
	
	public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent e) {
		Guild g = e.getGuild();
		Member m = e.getMember();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
        EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Nickname update");
		eb.addField("User: ", m.getAsMention(), false);
		eb.addField("Old Nick:", e.getOldNickname() + " ", false);
		eb.addField("New Nick:", e.getNewNickname() + " ", false);
		eb.setColor(gl.orange);
		eb.setFooter(stime, g.getIconUrl());
		if(gl.enabledLog(g, "guildmemberupdatenickname")) {
			gl.sendMsg(eb, g);
		}
		
	}

}
