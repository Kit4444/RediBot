package at.mlps.botclasses.guildlogging.guild;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter{
	
	//modlog (-> GuildMemberJoinWelcomer is for the Welcomer (if set))
	public void onGuildMemberJoin(GuildMemberJoinEvent e){
		Guild g = e.getGuild();
		Member m = e.getMember();
		GuildLogEvents gl = new GuildLogEvents();
		SimpleDateFormat time = new SimpleDateFormat("dd/MM/yy - HH:mm:ss");
        String stime = time.format(new Date());
		EmbedBuilder eb = new EmbedBuilder();
		eb.setColor(Color.green);
		eb.setTitle("Member joined");
		eb.setThumbnail(m.getUser().getAvatarUrl());
		eb.setDescription( m.getAsMention() + " has joined the guild. \n \nAccount Creation: " + gl.retDate(m.getTimeCreated()));
		eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		if(gl.enabledLog(g, "guildmemberjoin")) {
			gl.sendMsg(eb, g);
		}
	}

}
