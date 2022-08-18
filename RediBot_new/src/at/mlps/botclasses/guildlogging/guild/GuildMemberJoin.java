package at.mlps.botclasses.guildlogging.guild;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.ocpsoft.prettytime.PrettyTime;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildMemberJoin extends ListenerAdapter{
	
	long oneweek = 604800;
	long onemonth = 2592000;
	
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
		long creationDate = m.getTimeCreated().toEpochSecond();
		long oneWeekDate = creationDate + oneweek;
		long oneMonthDate = creationDate + onemonth;
		long currentDate = (System.currentTimeMillis() / 1000);
		boolean oneWeek = false;
		boolean oneMonth = false;
		if(oneWeekDate >= currentDate) {
			oneWeek = true;
		}
		if(oneMonthDate >= currentDate) {
			oneMonth = true;
		}
		PrettyTime pt = new PrettyTime();
		if(oneWeek) {
			eb.setDescription( m.getAsMention() + " has joined the guild. \n \nAccount Creation: " + gl.retDate(m.getTimeCreated()) + "\nAccount Age is less than one week! Age: " + pt.format(new Date(creationDate * 1000)));
		}else if(!oneWeek && oneMonth) {
			eb.setDescription( m.getAsMention() + " has joined the guild. \n \nAccount Creation: " + gl.retDate(m.getTimeCreated()) + "\nAccount Age is less than one month! Age: " + pt.format(new Date(creationDate * 1000)));
		}else {
			eb.setDescription( m.getAsMention() + " has joined the guild. \n \nAccount Creation: " + gl.retDate(m.getTimeCreated()) + "\nAccount Age: " + pt.format(new Date(creationDate * 1000)));
		}
		
		eb.setFooter(g.getName() + " - " + stime, g.getIconUrl());
		if(gl.enabledLog(g, "guildmemberjoin")) {
			gl.sendMsg(eb, g);
		}
	}

}
